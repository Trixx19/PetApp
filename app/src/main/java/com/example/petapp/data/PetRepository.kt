package com.example.petapp.data

import androidx.compose.runtime.mutableStateListOf
import com.example.petapp.R
import com.example.petapp.data.model.Appointment
import com.example.petapp.data.model.Pet
import com.example.petapp.data.model.Vaccine

object PetRepository {
    private val _petList = mutableStateListOf(
        // Exemplo do mock de dados completo de cada Pet
        //        Pet(
        //            id = 0  -> temos o id que aumenta a cada pet adicionado
        //            name = "nome", -> nome do pet
        //            specie = "espécie" -> espécie do pet(por enquanto apenas cachorro ou gato),
        //            breed = "raça - sexo", -> raça e sexo do pet, futuramente vou separar o sexo para fazer alguns filtros de busca
        //            birthDate = "00/00/0000", -> data de nascimento do pet, penso em adicionar uma cálculadora de idade na próxima evolução do código
        //            description = "descrição", -> descrição livre do seu pet, escolhido pelo dono
        //            imageRes = R.drawable.pet, -> imagem do pet
        //            vaccines = listOf( -> lista de vacinas registradas, penso em fazer um modelo que as vacina são adicionadas pela data, inicialmente em false e o usuário pode apertar o botão e trocar a vacina para true
        //                Vaccine("nome da vacina", "00/00/0000", true/false)
        //            ),
        //            appointments = listOf(
        //                Appointment("compromisso", "00/00/0000", "descrição detalhada do compromisso") -> lista de tarefas importantes para o pet, como passear, uma consulta, etc, juntamente com a vacina queria adicionar notificações para essa tela em uma entrega futura
        //            ),
        //            isFavorite = true/false -> funçao que deixa o favorite ligado ou desligado por padrão
        //        ),
        Pet(
            id = 1,
            name = "Snow",
            specie = "Cachorro",
            breed = "Husky - Macho",
            birthDate = "26/07/2017",
            description = "Extramamente carinhoso, calmo e adora longas sonecas",
            imageRes = R.drawable.snow,
            vaccines = listOf(
                Vaccine("V8", "01/05/2017", true),
                Vaccine("V10", "10/12/2019", true),
                Vaccine("Antirrábica", "14/07/2024", true),
                Vaccine("V8 Reforço", "04/09/2025", false)
            ),
            appointments = listOf(
                Appointment("Passeio", "29/05/2025", "Passeio diário"),
                Appointment("Consulta de rotina", "30/05/2025", "Avaliação geral de saúde."),
                Appointment("Banho", "20/06/2025", "Manutenção mensal de higiene.")
            ),
            isFavorite = true
        ),
        Pet(
            id = 2,
            name = "Salem",
            specie = "Gato",
            breed = "SRD - Macho",
            birthDate = "18/09/2020",
            description = "Muito danado, adora carinho e sache, ama ficar de preguiça",
            imageRes = R.drawable.salem,
            vaccines = listOf(
                Vaccine("V4", "26/04/2021", true),
                Vaccine("V5", "09/10/2022", true),
                Vaccine("Antirrábica", "09/11/2024", true),
                Vaccine("V5 Reforço", "29/12/2025", false)
            ),
            appointments = listOf(
                Appointment("Consulta de rotina", "02/04/2025", "Avaliação geral de saúde.")
            ),
            isFavorite = true
        ),
        Pet(
            id = 3,
            name = "Kiara ",
            specie = "Gato",
            breed = "SRD - Fêmea",
            birthDate = "18/09/2020",
            description = "Muito carinhosa e brincalhona, medrosa não gosta de pessoas de fora, ama sache e carinho",
            imageRes = R.drawable.kiara,
            vaccines = listOf(
                Vaccine("V4", "26/04/2021", true),
                Vaccine("V5", "09/10/2022", true),
                Vaccine("Antirrábica", "09/11/2024", true)
            ),
            appointments = listOf(
                Appointment("Check Up", "20/10/2024", "Consulta Dermatológica, coceira nas orelhas.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 4,
            name = "Mimo",
            specie = "Cachorro",
            breed = "Yorkshire - Macho",
            birthDate = "13/07/2013",
            description = "Muito gordo, adora comer e é extremamente sedentário. Precisa de cuidados especiais com a mobilidade.",
            imageRes = R.drawable.mimo,
            vaccines = listOf(
                Vaccine("Antirrábica", "12/01/2025", true),
                Vaccine("V8", "10/02/2024", true),
                Vaccine("Gripe Canina", "05/09/2024", false)
            ),
            appointments = listOf(
                Appointment("Consulta Ortopédica", "20/12/2024", "Consulta ortopédica para avaliar patinha quebrada alguns anos atrás. Foi feito um raio X."),
                Appointment("Check-up Geral", "15/03/2025", "Check-up completo para avaliar sobrepeso e saúde geral."),
                Appointment("Banho e Tosa", "21/05/2025", "Manutenção mensal de higiene.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 5,
            name = "Peter Parker",
            specie = "Cachorro",
            breed = "Shih Tzu - Macho",
            birthDate = "03/01/2022",
            description = "Estupidamente ativo, adora comer qualquer tipo de lixo e correr. Precisa de atenção constante com a pele devido a coceiras frequentes.",
            imageRes = R.drawable.parker, // Substituir pelo recurso correto da imagem
            vaccines = listOf(
                Vaccine("Antirrábica", "12/01/2025", true),
                Vaccine("V8", "18/02/2024", true),
                Vaccine("Gripe Canina", "10/09/2024", true)
            ),
            appointments = listOf(
                Appointment("Consulta Dermatológica", "28/05/2025", "Consulta com dermatologista devido a coceiras intensas. Avaliação para possíveis alergias ou dermatite."),
                Appointment("Check-up Anual", "10/01/2025", "Check-up geral para avaliar saúde, peso e vacinação."),
                Appointment("Banho e Tosa", "25/05/2025", "Manutenção de higiene e cuidados com a pele.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 6,
            name = "Nala",
            specie = "Cachorro",
            breed = "Misto - Vira-lata com Whippet - Fêmea",
            birthDate = "25/12/2018", // Coloquei como data de nascimento "Natal"
            description = "Corre muito rápido e dorme muito. Não gosta muito de pessoas além da família, então não tomou as vacinas",
            imageRes = R.drawable.nala, // Substituir pelo recurso correto da imagem
            vaccines = listOf(),
            appointments = listOf(
                Appointment("Consulta de Rotina", "18/06/2025", "Consulta para avaliar peso, saúde geral e desempenho físico, já que corre muito."),
                Appointment("Check-up Anual", "25/12/2024", "Check-up de aniversário para verificar vacinação e bem-estar."),
                Appointment("Banho e Tosa", "20/05/2025", "Manutenção de higiene, especialmente pelos curtos e finos típicos de Whippet.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 7,
            name = "Zayom",
            specie = "Cachorro",
            breed = "Yorkshire - Macho",
            birthDate = "10/04/2019", // Coloquei uma data plausível em 2019
            description = "Pequeno, muito agitado, adora brincar e late para qualquer barulho. É extremamente leal e carinhoso.",
            imageRes = R.drawable.zayom, // Substituir pelo recurso correto da imagem
            vaccines = listOf(
                Vaccine("Antirrábica", "05/02/2025", true),
                Vaccine("V8", "12/03/2024", true),
                Vaccine("Gripe Canina", "30/08/2024", false)
            ),
            appointments = listOf(),
            isFavorite = false
        ),
        Pet(
            id = 8,
            name = "Nickinho",
            specie = "Gato",
            breed = "SRD - Macho",
            birthDate = "15/06/2019", // Data fictícia dentro de 2019
            description = "Adora pular no colo dos outros pra pedir ração. Muito preguiçoso e vive deitado quase o dia todo.",
            imageRes = R.drawable.nickinho, // Substituir pelo recurso correto da imagem
            vaccines = listOf(
                Vaccine("Antirrábica", "08/01/2025", true),
                Vaccine("V4", "18/02/2024", true)
            ),
            appointments = listOf(
                Appointment("Consulta de Rotina", "10/04/2025", "Consulta geral para acompanhar sedentarismo e saúde geral."),
                Appointment("Banho", "05/06/2025", "Banho para higiene, já que ele passa boa parte do tempo deitado."),
                Appointment("Check-up Anual", "15/06/2025", "Check-up completo de aniversário, avaliação de peso e saúde geral.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 9,
            name = "Milly",
            specie = "Gato",
            breed = "SRD - Fêmea",
            birthDate = "10/05/2013", // Data fictícia dentro de 2020
            description = "Gata anti-social, mas adora carinho quando quer. Solta muitos tufos de pelo, especialmente durante o verão.",
            imageRes = R.drawable.milly, // Substituir pelo recurso correto da imagem
            vaccines = listOf(
                Vaccine("Antirrábica", "20/01/2025", true),
                Vaccine("V4 Felina", "12/03/2024", true),
                Vaccine("Leucemia Felina", "28/09/2024", false)
            ),
            appointments = listOf(
                Appointment("Consulta Dermatológica", "05/06/2025", "Avaliação devido à queda excessiva de pelos."),
                Appointment("Check-up Geral", "20/02/2025", "Check-up geral para avaliar saúde e comportamento anti-social."),
                Appointment("Banho e Escovação", "15/05/2025", "Sessão de banho e escovação para ajudar no controle dos pelos soltos.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 10,
            name = "Simba",
            specie = "Gato",
            breed = "SRD - Macho",
            birthDate = "05/04/2016", // Data fictícia dentro de 2016
            description = "Ex-gatinho de rua, não sabe miar direito e é um pouco vesguinho. Muito carinhoso e adaptado à vida em casa.",
            imageRes = R.drawable.simba, // Substituir pelo recurso correto da imagem
            vaccines = listOf(
                Vaccine("Antirrábica", "15/02/2025", true),
                Vaccine("V4 Felina", "30/05/2024", true),
                Vaccine("Leucemia Felina", "10/10/2024", true)
            ),
            appointments = listOf(
                Appointment("Consulta Oftalmológica", "12/03/2025", "Avaliação dos olhos por conta do estrabismo (vesguinho)."),
                Appointment("Check-up Geral", "18/01/2025", "Check-up geral para monitorar saúde após vida na rua."),
            ),
            isFavorite = false
        ),
        Pet(
            id = 11,
            name = "Kiara",
            specie = "Gato",
            breed = "SRD - Fêmea",
            birthDate = "10/09/2020", // Data fictícia dentro de 2020
            description = "Gatinha muito carinhosa, adora atentar a Milly, nunca cresce e tem o rabo macio.",
            imageRes = R.drawable.kiara2, // Ajuste para o recurso correto da imagem
            vaccines = listOf(
                Vaccine("Antirrábica", "18/01/2025", false),
                Vaccine("V4 Felina", "25/07/2024", true)
            ),
            appointments = listOf(
                Appointment("Consulta Comportamental", "05/05/2025", "Avaliação do comportamento, especialmente sua mania de provocar a Milly."),
                Appointment("Banho e Higiene Felina", "15/06/2025", "Sessão de higiene e escovação dos pelos.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 12,
            name = "Hiro",
            specie = "Cachorro",
            breed = "Hovawart - Macho",
            birthDate = "14/08/2020",
            description = "Cachorro extremamente leal, protetor e brincalhão. Adora correr no quintal, brincar de buscar bola e ficar perto dos tutores. Muito dócil com pessoas e outros animais.",
            imageRes = R.drawable.hiro, // Substituir pelo recurso de imagem correspondente
            vaccines = listOf(
                Vaccine("Antirrábica", "10/01/2025", true),
                Vaccine("V10", "20/06/2024", true),
                Vaccine("Gripe Canina", "05/09/2024", true)
            ),
            appointments = listOf(
                Appointment("Consulta de Rotina", "15/03/2025", "Check-up geral anual para avaliar peso, saúde dos dentes e articulações."),
                Appointment("Consulta Ortopédica", "30/09/2024", "Avaliação após uma pequena lesão na pata traseira durante brincadeiras intensas."),
                Appointment("Banho e Tosa", "12/05/2025", "Sessão de banho e tosa para manutenção do pelo longo típico da raça.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 13,
            name = "Sardinha Violência",
            specie = "Gato",
            breed = "SRD - Fêmea",
            birthDate = "09/02/2021",
            description = "Gata independente, temperamental, mas muito carinhosa quando quer. Adora observar tudo de longe e dormir nas caixas mais improváveis da casa. Apesar do nome, só distribui violência quando é contrariada.",
            imageRes = R.drawable.sardinha, // Substituir pelo recurso correto
            vaccines = listOf(
                Vaccine("Antirrábica", "12/01/2025", true),
                Vaccine("V4 Felina", "25/06/2024", false),
                Vaccine("Leucemia Felina", "10/10/2024", false)
            ),
            appointments = listOf(
                Appointment("Consulta Veterinária", "18/03/2025", "Check-up anual. Avaliação geral da saúde, com foco no controle de peso e pelagem."),
                Appointment("Banho (Raríssimo)", "05/07/2024", "Banho emergencial após cair acidentalmente em uma poça. Evento histórico.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 14,
            name = "Motosserra Misamore",
            specie = "Gato",
            breed = "SRD - Fêmea",
            birthDate = "15/08/2020",
            description = "Gatinha guerreira, muito carinhosa e brincalhona, mesmo sem um olhinho. Adora correr pela casa, derrubar objetos e caçar qualquer coisa que se mova. Seu ronronar parece uma motosserra de tão forte.",
            imageRes = R.drawable.motosserra, // Substituir pelo recurso correto
            vaccines = listOf(
                Vaccine("Antirrábica", "10/01/2025", false),
                Vaccine("V4 Felina", "05/07/2024", true),
                Vaccine("Leucemia Felina", "18/09/2024", false)
            ),
            appointments = listOf(
                Appointment("Consulta Oftalmológica", "12/04/2024", "Acompanhamento da condição do olhinho perdido. Avaliação de cicatrização e saúde geral."),
                Appointment("Check-up Geral", "20/02/2025", "Avaliação geral da saúde, incluindo controle de peso e vermifugação."),
                Appointment("Vacinação", "05/07/2024", "Aplicação da vacina V4 felina.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 15,
            name = "Toquinho Trompete Tracinha Jiji",
            specie = "Gato",
            breed = "SRD - Macho",
            birthDate = "12/10/2021",
            description = "Gatinho preto cheio de personalidade. É curioso, adora subir nos móveis, derrubar tudo que vê e se esconder nos lugares mais improváveis. Extremamente carinhoso, ama dormir no colo e ronrona igual a um motorzinho.",
            imageRes = R.drawable.trompete, // Substituir pelo recurso correto
            vaccines = listOf(
                Vaccine("Antirrábica", "08/01/2025", true),
                Vaccine("V4 Felina", "22/06/2024", false),
                Vaccine("Leucemia Felina", "18/10/2024", true)
            ),
            appointments = listOf(
                Appointment("Check-up Geral", "15/03/2025", "Avaliação de saúde geral, vermifugação e controle de pulgas."),
                Appointment("Consulta de Comportamento", "10/11/2024", "Orientações para lidar com comportamentos de escalada e bagunça."),
                Appointment("Vacinação", "22/06/2024", "Aplicação da vacina V4 felina e atualização do cartão de vacinação.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 16,
            name = "Nami",
            specie = "Gato",
            breed = "SRD - Fêmea",
            birthDate = "11/02/2020",
            description = "Nami é uma gata gorda, extremamente preguiçosa e dona de uma rotina muito bem definida: ela dorme, come e dorme mais um pouco. Inclusive, quando dorme demais, fica tão cansada que precisa tirar outra soneca para se recuperar do cansaço de ter dormido tanto.",
            imageRes = R.drawable.nami,
            vaccines = listOf(
                Vaccine("Antirrábica", "10/08/2024", true),
                Vaccine("V4 Felina", "12/09/2024", true),
                Vaccine("Leucemia Felina", "15/10/2024", false)
            ),
            appointments = listOf(
                Appointment("Check-up Geral", "20/03/2025", "Consulta de rotina para avaliar peso, saúde geral e dieta."),
                Appointment("Banho a Seco", "05/05/2025", "Limpeza e escovação dos pelos para manter ela ainda mais cheirosa."),
                Appointment("Consulta Nutricional", "18/06/2025", "Avaliação para controle de peso e alimentação saudável.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 17,
            name = "Moana",
            specie = "Cachorro",
            breed = "Yorkshire - Fêmea",
            birthDate = "15/09/2018",
            description = "Moana é uma Yorkshire cheia de personalidade.É extremamente carinhosa, adora ficar no colo e ser mimada.",
            imageRes = R.drawable.moana,
            vaccines = listOf(
                Vaccine("Antirrábica", "10/08/2024", true),
                Vaccine("V8", "15/03/2024", true),
                Vaccine("Gripe Canina", "20/10/2024", false)
            ),
            appointments = listOf(
                Appointment("Check-up Geral", "05/02/2025", "Avaliação geral de saúde e bem-estar."),
                Appointment("Banho e Tosa", "25/04/2025", "Tosa higiênica e banho especial para manter os pelos macios e cheirosos."),
                Appointment("Consulta Odontológica", "10/06/2025", "Limpeza dos dentes para manter a saúde bucal em dia.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 18,
            name = "Leo",
            specie = "Gato",
            breed = "Sem Raça Definida - Macho",
            birthDate = "12/03/2022",
            description = "Leo é um gato carinhoso, brincalhão e adora tomar banho de sol. Seus pelos claros e olhos atentos fazem dele um companheiro encantador.",
            imageRes = R.drawable.leo,
            vaccines = listOf(
                Vaccine("Antirrábica", "10/08/2024", true),
                Vaccine("V4", "15/03/2024", true),
                Vaccine("Leucemia Felina", "20/10/2024", false)
            ),
            appointments = listOf(
                Appointment("Check-up Geral", "05/02/2025", "Avaliação geral de saúde e bem-estar."),
                Appointment("Banho e Tosa", "25/04/2025", "Banho relaxante com produtos específicos para gatos."),
                Appointment("Consulta Odontológica", "10/06/2025", "Avaliação e limpeza dos dentes.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 19,
            name = "Princesa",
            specie = "Gato",
            breed = "Sem Raça Definida - Fêmea",
            birthDate = "12/03/2022",
            description = "Princesa é uma gata meiga, muito curiosa e adora ficar perto das pessoas. Seus pelos claros deixam sua beleza ainda mais evidente.",
            imageRes = R.drawable.princesa,
            vaccines = listOf(
                Vaccine("Antirrábica", "10/08/2024", true),
                Vaccine("V4", "15/03/2024", true),
                Vaccine("Leucemia Felina", "20/10/2024", false)
            ),
            appointments = listOf(
                Appointment("Check-up Geral", "05/02/2025", "Exame geral de saúde, está muito bem."),
                Appointment("Banho e Tosa", "25/04/2025", "Banho especial para gatos e escovação dos pelos."),
                Appointment("Consulta Odontológica", "10/06/2025", "Limpeza dos dentes para manter a saúde bucal.")
            ),
            isFavorite = false
        ),
        Pet(
            id = 20,
            name = "Lili",
            specie = "Gato",
            breed = "Sem Raça Definida - Fêmea",
            birthDate = "08/07/2021",
            description = "Lili é uma gatinha preta muito esperta que aprendeu a usar o vaso sanitário para suas necessidades. Super inteligente e independente!",
            imageRes = R.drawable.lili,
            vaccines = listOf(
                Vaccine("Antirrábica", "15/09/2024", true),
                Vaccine("V4", "10/02/2024", true),
                Vaccine("Leucemia Felina", "05/11/2024", false)
            ),
            appointments = listOf(
                Appointment("Check-up Geral", "12/03/2025", "Avaliação geral de saúde e comportamento."),
                Appointment("Banho e Tosa", "30/05/2025", "Banho especial para gatos e cuidados com o pelo preto."),
                Appointment("Consulta Odontológica", "20/07/2025", "Avaliação e limpeza dental.")
            ),
            isFavorite = false
        ),
        )
    val petList: List<Pet> get() = _petList
    // função que alterna o favorito
    fun toggleFavorite(petId: Int) {
        val index = _petList.indexOfFirst { it.id == petId }
        if (index != -1) {
            val pet = _petList[index]
            _petList[index] = pet.copy(isFavorite = !pet.isFavorite)
        }
    }

    // retorna a lista dos pets favoritados
    fun getFavorites(): List<Pet> {
        return _petList.filter { it.isFavorite }
    }

    // função que busca o pet pelo ID
    fun getPetById(petId: Int): Pet? {
        return _petList.find { it.id == petId }
    }
    // funçaõ de limpar todos os favoritos
    fun clearFavorites() {
        _petList.forEachIndexed { index, pet ->
            if (pet.isFavorite) {
                _petList[index] = pet.copy(isFavorite = false)
            }
        }
    }
}

