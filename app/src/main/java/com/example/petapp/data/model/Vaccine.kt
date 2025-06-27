package com.example.petapp.data.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Vaccine(
    val name: String,
    val date: String, // "dd/MM/yyyy"
    val isDone: Boolean
) {
    // Função auxiliar para converter a String de data para um objeto LocalDateTime
    // Vamos assumir um horário padrão para a notificação, como 09:00 da manhã.
    fun getLocalDateTime(): LocalDateTime? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val localDate = java.time.LocalDate.parse(date, formatter)
            localDate.atTime(9, 0) // Retorna a data às 09:00
        } catch (e: DateTimeParseException) {
            null // Retorna nulo se a data estiver em um formato inválido
        }
    }
}