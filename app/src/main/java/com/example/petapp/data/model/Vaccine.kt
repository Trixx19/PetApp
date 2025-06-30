// CLASSE DE DADOS PARA A VACINA
package com.example.petapp.data.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Vaccine(
    val name: String, // nome da vacina
    val date: String, // data
    val isDone: Boolean // check se foi tomada ou não
) {
    // função auxiliar para converter a data
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