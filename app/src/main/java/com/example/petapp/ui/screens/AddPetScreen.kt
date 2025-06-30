// app/src/main/java/com/example/petapp/ui/screens/AddPetScreen.kt
package com.example.petapp.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.R // Para acessar os drawables
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Pet
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var specie by remember { mutableStateOf("Cachorro") } // Espécie padrão
    var breed by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("Macho") } // Sexo padrão
    // --- NOVO: Captura a data inicial para comparação ---
    val initialBirthDate = remember { LocalDate.now() }
    var birthDate by remember { mutableStateOf(initialBirthDate) }
    // --- FIM NOVO ---
    var description by remember { mutableStateOf("") }
    var selectedImageRes by remember { mutableStateOf(R.drawable.icon) } // Imagem padrão

    val context = LocalContext.current

    // Formatador para exibir a data
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Diálogo para seleção de data
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            birthDate = LocalDate.of(year, month + 1, dayOfMonth)
        },
        birthDate.year,
        birthDate.monthValue - 1,
        birthDate.dayOfMonth
    )

    // --- Início do bloco de cálculo do progresso (NOVA LÓGICA) ---
    val progress by remember {
        derivedStateOf {
            var completedFields = 0
            // Agora, consideramos apenas os campos que o usuário precisa preencher
            // Nome, Raça, Descrição, Data de Nascimento (se alterada)
            val totalFields = 4

            if (name.isNotBlank()) completedFields++
            if (breed.isNotBlank()) completedFields++
            if (description.isNotBlank()) completedFields++
            // Conta a data apenas se ela for diferente da data inicial carregada
            if (birthDate != initialBirthDate) completedFields++

            // Garante que o progresso não exceda 1.0f (100%) e seja no mínimo 0.0f
            (completedFields.toFloat() / totalFields).coerceIn(0f, 1f)
        }
    }
    // --- Fim do bloco de cálculo do progresso (NOVA LÓGICA) ---

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Novo Pet") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp) // Mantém padding horizontal
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // --- LinearProgressIndicator foi mantido no topo da Column ---
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espaçamento abaixo da barra

            // Seleção de Imagem (Ícone de Gato/Cachorro)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Escolha o ícone do pet:", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.width(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.icondog), // Ícone de cachorro
                    contentDescription = "Ícone de Cachorro",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (selectedImageRes == R.drawable.icondog) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            selectedImageRes = R.drawable.icondog
                            specie = "Cachorro" // Atualiza a espécie baseada na imagem
                        }
                )
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.iconcat), // Ícone de gato
                    contentDescription = "Ícone de Gato",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (selectedImageRes == R.drawable.iconcat) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            selectedImageRes = R.drawable.iconcat
                            specie = "Gato" // Atualiza a espécie baseada na imagem
                        }
                )
            }
            Spacer(Modifier.height(8.dp))

            // Campo Nome
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome do Pet") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Raça
            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = { Text("Raça") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Seleção de Sexo (Macho/Fêmea)
            Text("Sexo", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = sex == "Macho", onClick = { sex = "Macho" })
                    Text("Macho", Modifier.clickable { sex = "Macho" })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = sex == "Fêmea", onClick = { sex = "Fêmea" })
                    Text("Fêmea", Modifier.clickable { sex = "Fêmea" })
                }
            }

            // Campo Data de Nascimento
            Button(onClick = { datePickerDialog.show() }) {
                Text("Data de Nascimento: ${birthDate.format(dateFormatter)}")
            }

            // Campo Descrição
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Salvar Pet
            Button(
                onClick = {
                    if (name.isBlank() || breed.isBlank()) {
                        Toast.makeText(context, "Nome e Raça são obrigatórios!", Toast.LENGTH_SHORT).show()
                    } else {
                        val newPet = Pet(
                            id = PetRepository.petList.size + 1, // ID simples (pode ser melhorado para algo único)
                            name = name,
                            specie = specie,
                            breed = breed,
                            sex = sex,
                            birthDate = birthDate.format(dateFormatter),
                            description = description,
                            imageRes = selectedImageRes,
                            vaccines = listOf(),
                            appointments = listOf(),
                            isFavorite = false,
                            reminders = mutableListOf()
                        )
                        PetRepository.addPet(newPet) // Chamada para adicionar o pet
                        Toast.makeText(context, "Pet salvo com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Salvar Pet")
            }
        }
    }
}