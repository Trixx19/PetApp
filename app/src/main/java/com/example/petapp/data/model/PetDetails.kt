package com.example.petapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val title: String,
    val dateTime: Long,
    val isRepeating: Boolean = false
)

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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
    val isFavorite: Boolean = false
)