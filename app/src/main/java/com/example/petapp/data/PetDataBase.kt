package com.example.petapp.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.petapp.data.model.Pet

// ATUALIZA A VERSÃO DO BANCO DE DADOS DE 4 PARA 5
@Database(entities = [Pet::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PetDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var Instance: PetDatabase? = null

        // Migrações existentes
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

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE pets ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
            }
        }

        // NOVA MIGRAÇÃO PARA ADICIONAR A COLUNA needsSync
        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Adiciona a nova coluna, com valor padrão 1 (true), indicando que precisa de sincronização
                db.execSQL("ALTER TABLE pets ADD COLUMN needsSync INTEGER NOT NULL DEFAULT 1")
            }
        }

        fun getDatabase(context: Context): PetDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PetDatabase::class.java, "pet_database")
                    // ADICIONA A NOVA MIGRAÇÃO À LISTA
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}