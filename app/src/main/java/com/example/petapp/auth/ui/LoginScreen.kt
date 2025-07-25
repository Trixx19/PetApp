package com.example.petapp.auth.ui

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
// IMPORTAÇÕES ADICIONADAS
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.R
import com.example.petapp.auth.viewmodel.AuthViewModel
import com.example.petapp.ui.navigation.PetDestinations
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Launcher para o resultado do Login com Google
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            coroutineScope.launch {
                isLoading = true
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    val account = task.getResult(ApiException::class.java)
                    val googleIdToken = account?.idToken

                    if (googleIdToken != null) {
                        authViewModel.loginWithGoogle(googleIdToken) { /* O resultado não importa aqui */ }
                        // Pausa para garantir que o Firebase processe o login
                        delay(2000)
                        navController.navigate(PetDestinations.HOME_ROUTE) {
                            popUpTo(PetDestinations.LOGIN_ROUTE) { inclusive = true }
                        }
                    } else {
                        errorMessage = "Não foi possível obter o token do Google."
                        isLoading = false
                    }
                } catch (e: ApiException) {
                    isLoading = false
                    errorMessage = "Falha na autenticação com Google: ${e.statusCode}"
                    Log.e("LoginScreen", "Google sign-in failed", e)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bem-vindo ao PetApp", style = MaterialTheme.typography.headlineLarge)
            Text(
                "Faça login para continuar",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Campos de Email e Senha
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Entrar (Email/Senha)
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        isLoading = true
                        errorMessage = null
                        authViewModel.login(email, password) { success ->
                            if (success) {
                                navController.navigate(PetDestinations.HOME_ROUTE) {
                                    popUpTo(PetDestinations.LOGIN_ROUTE) { inclusive = true }
                                }
                            } else {
                                errorMessage = "Usuário ou senha inválida."
                                isLoading = false
                            }
                        }
                    } else {
                        errorMessage = "Preencha todos os campos."
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = !isLoading
            ) {
                Text("Entrar", fontSize = 18.sp)
            }

            // ESPAÇO ADICIONADO AQUI
            Spacer(modifier = Modifier.height(16.dp))

            // Links centralizados para Criar Conta e Esqueci a Senha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = AnnotatedString("Criar Conta"),
                    onClick = { navController.navigate(PetDestinations.REGISTER_ROUTE) },
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
                Text(" | ", modifier = Modifier.padding(horizontal = 8.dp))
                ClickableText(
                    text = AnnotatedString("Esqueci a senha"),
                    onClick = { navController.navigate(PetDestinations.FORGOT_PASSWORD_ROUTE) },
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            }

            // Divisor "ou"
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                Divider(modifier = Modifier.weight(1f))
                Text(" ou ", modifier = Modifier.padding(horizontal = 8.dp))
                Divider(modifier = Modifier.weight(1f))
            }

            // Botões de Login Social
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Botão de Entrar com Google
                OutlinedButton(
                    onClick = {
                        isLoading = true
                        errorMessage = null
                        val signInIntent = authViewModel.getGoogleSignInClient(context).signInIntent
                        googleSignInLauncher.launch(signInIntent)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.logo_google), contentDescription = "Google Logo", modifier = Modifier.size(24.dp))
                    Text("  Entrar com Google", fontSize = 18.sp, modifier = Modifier.padding(start = 8.dp))
                }

                // Botão de Entrar com GitHub
                OutlinedButton(
                    onClick = {
                        if (activity != null) {
                            isLoading = true
                            authViewModel.loginWithGitHub(activity) { success ->
                                if (success) {
                                    coroutineScope.launch {
                                        delay(1500)
                                        navController.navigate(PetDestinations.HOME_ROUTE) {
                                            popUpTo(PetDestinations.LOGIN_ROUTE) { inclusive = true }
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Erro ao fazer login com GitHub.", Toast.LENGTH_SHORT).show()
                                    isLoading = false
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Entrar com GitHub", fontSize = 18.sp)
                }
            }
        }

        // Indicador de carregamento geral
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}