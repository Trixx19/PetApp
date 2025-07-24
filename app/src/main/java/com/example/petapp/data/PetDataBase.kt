package com.example.petapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.petapp.data.model.Pet

@Database(entities = [Pet::class], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var INSTANCE: PetDatabase? = null

        fun getDatabase(context: Context): PetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetDatabase::class.java,
                    "pet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}