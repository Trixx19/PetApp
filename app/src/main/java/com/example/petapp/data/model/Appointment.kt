// CLASSE DE DADOS DOS COMPROMISSOS
package com.example.petapp.data.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Appointment(
    val title: String, // nome do compromisso
    val date: String, // data com dia/mes/ano
    val description: String // descrição do compormisso
) {
    // função auxiliar similar à de vacina
    fun getLocalDateTime(): LocalDateTime? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val localDate = java.time.LocalDate.parse(date, formatter)
            localDate.atTime(9, 0)
        } catch (e: DateTimeParseException) {
            null
        }
    }
}