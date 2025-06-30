// CLASSE DE DADOS DO PET
package com.example.petapp.data.model

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
)