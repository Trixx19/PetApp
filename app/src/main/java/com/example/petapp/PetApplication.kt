package com.example.petapp

import android.app.Application
import com.example.petapp.data.PetDatabase
import com.example.petapp.data.PetRepository

class PetApplication : Application() {
    private val database: PetDatabase by lazy { PetDatabase.getDatabase(this) }
    val repository: PetRepository by lazy { PetRepository(database.petDao()) }
}