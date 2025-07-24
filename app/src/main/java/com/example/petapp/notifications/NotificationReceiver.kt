package com.example.petapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("NOTIFICATION_TITLE") ?: "Lembrete do PetApp"
        val message = intent.getStringExtra("NOTIFICATION_MESSAGE") ?: "Você tem um novo lembrete!"

        val notificationHelper = NotificationHelper(context)
        notificationHelper.createNotificationChannel() // Garante que o canal existe
        notificationHelper.sendNotification(title, message)
    }
}