package com.example.petapp.data.model

import java.time.LocalDateTime

enum class Priority {
    HIGH, LOW
}

data class Reminder(
    val id: Int = System.currentTimeMillis().toInt(), // ID único para o lembrete
    val title: String,
    val dateTime: LocalDateTime,
    val priority: Priority
)