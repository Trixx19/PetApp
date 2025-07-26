package com.example.petapp.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class ImageStorageManager(private val context: Context) {

    // Salva uma imagem a partir de uma Uri e retorna o caminho do arquivo salvo
    fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            // Abre o fluxo de dados da imagem selecionada na galeria
            val inputStream = context.contentResolver.openInputStream(uri)

            // Decodifica o fluxo em um Bitmap
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Cria um nome de arquivo único
            val filename = "${UUID.randomUUID()}.jpg"

            // Define o arquivo de destino na pasta privada do app
            val file = File(context.filesDir, filename)

            // Abre o fluxo de escrita para o novo arquivo
            val outputStream = FileOutputStream(file)

            // Comprime e salva o bitmap no novo arquivo
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)

            // Fecha os fluxos
            outputStream.flush()
            outputStream.close()
            inputStream?.close()

            // Retorna o caminho absoluto do arquivo salvo
            Log.d("ImageStorageManager", "Imagem salva em: ${file.absolutePath}")
            file.absolutePath
        } catch (e: Exception) {
            Log.e("ImageStorageManager", "Erro ao salvar imagem", e)
            null
        }
    }

    // Deleta uma imagem do armazenamento interno com base no caminho
    fun deleteImageFromInternalStorage(filePath: String): Boolean {
        return try {
            val file = File(filePath)
            if (file.exists()) {
                file.delete()
                Log.d("ImageStorageManager", "Imagem deletada: $filePath")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("ImageStorageManager", "Erro ao deletar imagem", e)
            false
        }
    }
}