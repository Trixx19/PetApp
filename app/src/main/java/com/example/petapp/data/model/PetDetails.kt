package com.example.petapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

data class Vaccine(
    val name: String,
    val date: String,
    val nextDueDate: String? = null
)

data class Appointment(
    val type: String,
    val date: String,
    val time: String,
    val location: String? = null,
    val notes: String? = null
)

data class Reminder(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val dateTime: Long,
    val isRepeating: Boolean = false,
    val isCompleted: Boolean = false
)

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    // CAMPO ADICIONADO PARA ASSOCIAR O PET AO USUÁRIO
    var userId: String = "",
    val name: String,
    val specie: String,
    val breed: String,
    val sex: String,
    val birthDate: String,
    val description: String,
    val imageUrl: String = "",
    val vaccines: List<Vaccine> = listOf(),
    val appointments: List<Appointment> = listOf(),
    val reminders: List<Reminder> = listOf(),
    val isFavorite: Boolean = false,
    val needsSync: Boolean = true
)