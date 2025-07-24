package com.example.petapp.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.petapp.data.model.Pet

@Database(entities = [Pet::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PetDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var Instance: PetDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE pets ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE pets ADD COLUMN specie TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE pets ADD COLUMN breed TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE pets ADD COLUMN sex TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE pets ADD COLUMN birthDate TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE pets ADD COLUMN imageUrl TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE pets ADD COLUMN vaccines TEXT NOT NULL DEFAULT '[]'")
                db.execSQL("ALTER TABLE pets ADD COLUMN appointments TEXT NOT NULL DEFAULT '[]'")
                db.execSQL("ALTER TABLE pets ADD COLUMN reminders TEXT NOT NULL DEFAULT '[]'")
            }
        }

        fun getDatabase(context: Context): PetDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PetDatabase::class.java, "pet_database")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}