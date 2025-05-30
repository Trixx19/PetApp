package com.example.petapp.ui.components

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.*

@Composable // composable opcional que faz a reprodução de áudio no app
fun PlaySoundEffect(
    context: Context,
    resId: Int,
    play: Boolean
) {
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    LaunchedEffect(play) {
        if (play) {
            mediaPlayer?.release() // libera o áudio anterior
            mediaPlayer = MediaPlayer.create(context, resId) // cria um novo com o atual
            mediaPlayer?.start() // reproduz o audio atual
        }
    }
    DisposableEffect(Unit) { // serve apenas para liberar corretamente recursos usados pelo áudio
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}
