package com.example.petapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.petapp.R

class NotificationHelper(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val HIGH_PRIORITY_CHANNEL_ID = "high_priority_channel"
        const val LOW_PRIORITY_CHANNEL_ID = "low_priority_channel"
    }

    // Cria os canais de notificação. Essencial para Android 8 (Oreo) e superior.
    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Canal de Alta Prioridade (para vacinas e compromissos importantes)
            val highPriorityChannel = NotificationChannel(
                HIGH_PRIORITY_CHANNEL_ID,
                "Lembretes Importantes",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificações para vacinas e compromissos urgentes."
            }
            notificationManager.createNotificationChannel(highPriorityChannel)

            // Canal de Baixa Prioridade (para tarefas comuns)
            val lowPriorityChannel = NotificationChannel(
                LOW_PRIORITY_CHANNEL_ID,
                "Lembretes Comuns",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificações para banhos, passeios e outras tarefas."
            }
            notificationManager.createNotificationChannel(lowPriorityChannel)
        }
    }

    // Constrói e exibe a notificação
    fun sendNotification(
        channelId: String,
        title: String,
        contentText: String,
        notificationId: Int
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use um ícone adequado
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(
                if (channelId == HIGH_PRIORITY_CHANNEL_ID) NotificationCompat.PRIORITY_HIGH
                else NotificationCompat.PRIORITY_DEFAULT
            )
            .setAutoCancel(true) // A notificação some ao ser tocada

        notificationManager.notify(notificationId, builder.build())
    }
}