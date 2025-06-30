// app/src/main/java/com/example/petapp/notifications/NotificationHelper.kt
package com.example.petapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.petapp.R // Para acessar os recursos drawable

class NotificationHelper(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val HIGH_PRIORITY_CHANNEL_ID = "high_priority_channel"
        const val MEDIUM_PRIORITY_CHANNEL_ID = "medium_priority_channel"
        const val LOW_PRIORITY_CHANNEL_ID = "low_priority_channel"
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
                description = "Notificações para vacinas, compromissos críticos e lembretes urgentes."
                enableLights(true)
                lightColor = context.getColor(R.color.purple_500)
                enableVibration(true)
                // Use seu som personalizado aqui, por exemplo:
                // setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.squeak), audioAttributes)
                // Ou o som padrão do sistema:
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes)
            }
            notificationManager.createNotificationChannel(highPriorityChannel)

            val mediumPriorityChannel = NotificationChannel(
                MEDIUM_PRIORITY_CHANNEL_ID,
                "Lembretes Comuns",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificações para banhos, passeios e outras tarefas gerais."
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(mediumPriorityChannel)

            val lowPriorityChannel = NotificationChannel(
                LOW_PRIORITY_CHANNEL_ID,
                "Lembretes Opcionais",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notificações para informações menos urgentes que não precisam de interrupção."
                setSound(null, null)
                enableVibration(false)
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
            // --- ALTERAÇÃO AQUI: Use um ícone mais adequado, por exemplo R.drawable.ic_notification_bell ---
            // Certifique-se que o ícone exista na pasta res/drawable
            .setSmallIcon(R.drawable.notificationicon) // <--- Mude para o ícone desejado
            // Se você não tiver um ícone específico, pode usar R.drawable.icon (o ícone do seu app)
            // ou R.drawable.ic_launcher_foreground para um ícone genérico.
            // .setSmallIcon(R.drawable.icon) // Exemplo: ícone do app
            // .setSmallIcon(R.drawable.ic_launcher_foreground) // O ícone que estava antes
            // --- FIM DA ALTERAÇÃO ---
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(
                when (channelId) {
                    HIGH_PRIORITY_CHANNEL_ID -> NotificationCompat.PRIORITY_HIGH
                    MEDIUM_PRIORITY_CHANNEL_ID -> NotificationCompat.PRIORITY_DEFAULT
                    else -> NotificationCompat.PRIORITY_LOW
                }
            )
            .setAutoCancel(true)

        notificationManager.notify(notificationId, builder.build())
    }
}