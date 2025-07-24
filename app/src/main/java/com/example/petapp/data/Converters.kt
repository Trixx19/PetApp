package com.example.petapp.data

import androidx.room.TypeConverter
import com.example.petapp.data.model.Appointment
import com.example.petapp.data.model.Reminder
import com.example.petapp.data.model.Vaccine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromVaccineList(vaccines: List<Vaccine>): String = gson.toJson(vaccines)

    @TypeConverter
    fun toVaccineList(vaccinesString: String): List<Vaccine> {
        val listType = object : TypeToken<List<Vaccine>>() {}.type
        return gson.fromJson(vaccinesString, listType)
    }

    @TypeConverter
    fun fromAppointmentList(appointments: List<Appointment>): String = gson.toJson(appointments)

    @TypeConverter
    fun toAppointmentList(appointmentsString: String): List<Appointment> {
        val listType = object : TypeToken<List<Appointment>>() {}.type
        return gson.fromJson(appointmentsString, listType)
    }

    @TypeConverter
    fun fromReminderList(reminders: List<Reminder>): String = gson.toJson(reminders)

    @TypeConverter
    fun toReminderList(remindersString: String): List<Reminder> {
        val listType = object : TypeToken<List<Reminder>>() {}.type
        return gson.fromJson(remindersString, listType)
    }
}