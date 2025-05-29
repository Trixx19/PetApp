package com.example.petapp.data

import com.example.petapp.R
import com.example.petapp.data.model.Appointment
import com.example.petapp.data.model.Pet
import com.example.petapp.data.model.Vaccine

object PetRepository {
    val petList = listOf(
        Pet(
            id = 1,
            name = "Luna",
            specie = "Cachorro",
            breed = "Labrador",
            birthDate = "10/04/2019",
            description = "Brincalhona, adora correr no parque e muito carinhosa com crianças.",
            imageRes = R.drawable.pet1,
            vaccines = listOf(
                Vaccine("Antirrábica", "10/04/2023", true),
                Vaccine("V8", "10/10/2023", true),
                Vaccine("V8 Reforço", "10/10/2024", false)
            ),
            appointments = listOf(
                Appointment("Consulta de rotina", "05/05/2025", "Avaliação geral de saúde."),
                Appointment("Banho e Tosa", "20/06/2025", "Manutenção mensal de higiene.")
            ),
            isFavorite = true
        ),
        Pet(
            id = 2,
            name = "Milo",
            specie = "Gato",
            breed = "Siamês",
            birthDate = "23/06/2021",
            description = "Gato curioso, gosta de explorar e tirar longos cochilos.",
            imageRes = R.drawable.pet2,
            vaccines = listOf(
                Vaccine("Tríplice Felina", "01/02/2024", true),
                Vaccine("Antirrábica", "01/02/2024", true)
            ),
            appointments = listOf(
                Appointment("Consulta veterinária", "15/07/2025", "Check-up anual."),
                Appointment("Vacinação de reforço", "01/02/2025", "Aplicação da tríplice felina reforço.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 3,
            name = "Bella",
            specie = "Cachorro",
            breed = "Poodle",
            birthDate = "05/12/2020",
            description = "Muito dócil, ama colo e brincar com bolinhas.",
            imageRes = R.drawable.pet3,
            vaccines = listOf(
                Vaccine("Antirrábica", "10/12/2023", true),
                Vaccine("V8", "10/06/2023", true)
            ),
            appointments = listOf(
                Appointment("Vacinação Reforço", "10/06/2024", "Reforço da V8."),
                Appointment("Consulta Odontológica", "22/08/2025", "Avaliação dos dentes.")
            ),
            isFavorite = true
        ),
        Pet(
            id = 4,
            name = "Max",
            specie = "Cachorro",
            breed = "Golden Retriever",
            birthDate = "17/09/2018",
            description = "Muito ativo, adora água e corridas longas.",
            imageRes = R.drawable.pet4,
            vaccines = listOf(
                Vaccine("Antirrábica", "15/09/2023", true),
                Vaccine("V10", "15/03/2024", true)
            ),
            appointments = listOf(
                Appointment("Consulta ortopédica", "10/10/2025", "Avaliação das articulações."),
                Appointment("Hidroterapia", "05/11/2025", "Atividade física supervisionada.")
            ),
            isFavorite = false
        )
    )
}
