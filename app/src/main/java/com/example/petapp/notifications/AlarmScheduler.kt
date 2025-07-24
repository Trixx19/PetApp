package com.example.petapp.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.petapp.data.model.Reminder
import java.time.Instant
import java.time.ZoneId

interface AlarmScheduler {
    fun schedule(reminder: Reminder)
    fun cancel(reminder: Reminder)
}

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(reminder: Reminder) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("NOTIFICATION_TITLE", reminder.title)
            putExtra("NOTIFICATION_MESSAGE", "Lembrete para o seu pet!")
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            reminder.dateTime, // O dateTime já está em milissegundos (Long)
            PendingIntent.getBroadcast(
                context,
                reminder.hashCode(), // Usar o hashCode do lembrete como ID único
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(reminder: Reminder) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                reminder.hashCode(),
                Intent(context, NotificationReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}