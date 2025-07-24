package com.example.petapp


import com.example.petapp.data.PetRepository
import android.app.Application
import com.example.petapp.data.PetDatabase

class PetApplication : Application() {
    val database: PetDatabase by lazy { PetDatabase.getDatabase(this) }
    val repository: PetRepository by lazy { PetRepository(database.petDao()) }
}