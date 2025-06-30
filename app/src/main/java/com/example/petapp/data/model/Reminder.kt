// NOVA CLASSE DE DADOS DOS LEMBRETES
package com.example.petapp.data.model

import java.time.LocalDateTime

enum class Priority { // prioridades diferentes para os lembretes
    HIGH, MEDIUM, LOW
}
data class Reminder(
    val id: Int = System.currentTimeMillis().toInt(), // id único para cada lembrete
    val title: String, // título para o lembrete
    val dateTime: LocalDateTime, // data e hora para o lembrete
    val priority: Priority // prioridade do lembrete
)