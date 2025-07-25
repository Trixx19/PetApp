package com.example.petapp.auth.data

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.petapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    suspend fun registerUser(email: String, password: String, name: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.uid?.let { saveUserData(it, name, email) }
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro no cadastro: ${e.message}")
            false
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro no login: ${e.message}")
            false
        }
    }

    suspend fun resetPassword(email: String): Boolean {
        return try {
            auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro ao enviar email de recuperação: ${e.message}")
            false
        }
    }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    suspend fun loginWithGoogle(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            result.user?.let {
                val userRef = firestore.collection("users").document(it.uid)
                val snapshot = userRef.get().await()
                if (!snapshot.exists()) {
                    saveUserData(it.uid, it.displayName ?: "Usuário Google", it.email)
                }
            }
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro no login Google: ${e.message}")
            false
        }
    }

    fun loginWithGitHub(activity: Activity, onResult: (Boolean) -> Unit) {
        val provider = OAuthProvider.newBuilder("github.com").build()
        auth.startActivityForSignInWithProvider(activity, provider)
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    val userRef = firestore.collection("users").document(it.uid)
                    userRef.get().addOnSuccessListener { document ->
                        if (!document.exists()) {
                            saveUserData(it.uid, it.displayName ?: "Usuário GitHub", it.email)
                        }
                    }
                }
                onResult(true)
            }
            .addOnFailureListener { e ->
                Log.e("AuthRepository", "Falha no login com GitHub: ${e.message}", e)
                onResult(false)
            }
    }

    // FUNÇÃO ADICIONADA
    suspend fun getUserName(): String? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val snapshot = firestore.collection("users").document(uid).get().await()
            snapshot.getString("name")
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro ao buscar nome do usuário: ${e.message}")
            null
        }
    }

    private fun saveUserData(uid: String, name: String, email: String?) {
        val userData = hashMapOf(
            "uid" to uid,
            "name" to name,
            "email" to (email ?: ""),
            "created_at" to System.currentTimeMillis()
        )
        // Usamos addOnSuccessListener e addOnFailureListener para não precisar de 'await()'
        firestore.collection("users").document(uid).set(userData)
            .addOnSuccessListener { Log.d("AuthRepository", "Dados do usuário salvos com sucesso.") }
            .addOnFailureListener { e -> Log.e("AuthRepository", "Erro ao salvar dados do usuário.", e) }
    }


    fun logout(context: Context) {
        auth.signOut()
        // Importante: Fazer logout do cliente do Google também
        getGoogleSignInClient(context).signOut()
    }
}