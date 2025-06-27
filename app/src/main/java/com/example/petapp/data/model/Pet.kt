package com.example.petapp.data.model

// Adicione a lista de lembretes ao final dos parâmetros
data class Pet(
    val id: Int,
    val name: String,
    val specie: String,
    val breed: String,
    val birthDate: String,
    val description: String,
    val imageRes: Int,
    val vaccines: List<Vaccine> = listOf(),
    val appointments: List<Appointment> = listOf(),
    val isFavorite: Boolean = false,
    val reminders: MutableList<Reminder> = mutableListOf() // Nova lista de lembretes
)