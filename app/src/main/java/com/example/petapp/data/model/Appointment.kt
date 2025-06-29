package com.example.petapp.data.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Appointment(
    val title: String,
    val date: String, // "dd/MM/yyyy"
    val description: String
) {
    // Função auxiliar similar à de Vaccine
    fun getLocalDateTime(): LocalDateTime? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val localDate = java.time.LocalDate.parse(date, formatter)
            localDate.atTime(9, 0) // Retorna a data às 09:00
        } catch (e: DateTimeParseException) {
            null
        }
    }
}