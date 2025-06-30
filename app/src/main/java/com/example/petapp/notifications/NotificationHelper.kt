// CLASSE PARA CRIAR AS NOTIFICAÇÕES
package com.example.petapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.petapp.R

class NotificationHelper(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    companion object {
        const val HIGH_PRIORITY_CHANNEL_ID = "high_priority_channel" // notificação de alta prioridade aparece na tela, vibra e produz som
        const val MEDIUM_PRIORITY_CHANNEL_ID = "medium_priority_channel" // notificação de média prioridade vibra e produz som
        const val LOW_PRIORITY_CHANNEL_ID = "low_priority_channel" // notificação de baixa prioridade apenas vibra
    }
    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                .build()

            val highPriorityChannel = NotificationChannel(
                HIGH_PRIORITY_CHANNEL_ID,
                "Lembretes Urgentes",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificações para lembretes críticos."
                enableLights(true)
                lightColor = context.getColor(R.color.purple_500)
                enableVibration(true)
                // som personalizado de notificação
                setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.squeak), audioAttributes)
            }
            notificationManager.createNotificationChannel(highPriorityChannel)

            val mediumPriorityChannel = NotificationChannel(
                MEDIUM_PRIORITY_CHANNEL_ID,
                "Lembretes Comuns",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificações lembretes gerais"
                enableVibration(true)
                setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.squeak), audioAttributes)
            }
            notificationManager.createNotificationChannel(mediumPriorityChannel)

            val lowPriorityChannel = NotificationChannel(
                LOW_PRIORITY_CHANNEL_ID,
                "Lembretes Opcionais",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notificações para informações menos urgentes."
                setSound(null, null)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(lowPriorityChannel)
        }
    }
    // constroi e envia a notificação
    fun sendNotification(
        channelId: String,
        title: String,
        contentText: String,
        notificationId: Int
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            // icone específico para a notificação
            .setSmallIcon(R.drawable.notificationicon)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(
                when (channelId) {
                    HIGH_PRIORITY_CHANNEL_ID -> NotificationCompat.PRIORITY_HIGH
                    MEDIUM_PRIORITY_CHANNEL_ID -> NotificationCompat.PRIORITY_DEFAULT
                    else -> NotificationCompat.PRIORITY_LOW
                }
            ).setAutoCancel(true)
        notificationManager.notify(notificationId, builder.build())
    }
}