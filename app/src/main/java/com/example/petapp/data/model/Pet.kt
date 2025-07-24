// CLASSE DE DADOS DO PET
package com.example.petapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets") // Define o nome da tabela
data class Pet(
    @PrimaryKey(autoGenerate = true) // Define a chave primária com autoincremento
    val id: Int = 0,
    val name: String,
    val specie: String,
    val breed: String,
    val sex: String,
    val birthDate: String,
    val description: String,
    var imageUrl: String? = null,
    val isFavorite: Boolean = false
)
/*
data class Pet(
    val id: Int, // id do pet
    val name: String, // nome
    val specie: String, // espécie - gato ou cachorro
    val breed: String, // raça
    val sex: String, // sexo
    val birthDate: String, // data de nascimento
    val description: String, // descrição
    val imageRes: Int, // foto do pet
    val vaccines: List<Vaccine> = listOf(), // vacinas
    val appointments: List<Appointment> = listOf(), // compromissos
    val isFavorite: Boolean = false, // switch de favorito
    val reminders: MutableList<Reminder> = mutableListOf() // novos lembretes para usar notificações
)*/