package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// modelo de dados das informações
data class InformationItem(
    val title: String,
    val description: String,
    val details: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    onNavigateUp: () -> Unit // Usando onNavigateUp para consistência
) {
    val infoList = listOf(
        InformationItem(
            title = "Vacinas Essenciais para Cães",
            description = "Proteja seu cão contra doenças comuns e perigosas.",
            details = listOf(
                "V8/V10 (Múltipla): Protege contra cinomose, parvovirose, hepatite, adenovirose, parainfluenza, coronavirose e leptospirose. Reforço anual.",
                "Antirrábica: Obrigatória, previne a raiva. Reforço anual.",
                "Gripe Canina: Protege contra tosse dos canis. Reforço anual ou semestral, dependendo da exposição.",
                "Giardíase: Previne infecção por giárdia. Reforço anual."
            )
        ),
        InformationItem(
            title = "Vacinas Essenciais para Gatos",
            description = "Mantenha seu gato saudável e imune.",
            details = listOf(
                "V3/V4/V5 (Múltipla Felina): Protege contra rinotraqueíte, calicivirose, panleucopenia e, em algumas, clamidiose e leucemia felina. Reforço anual.",
                "Antirrábica: Obrigatória em muitas regiões, previne a raiva. Reforço anual.",
                "Leucemia Felina (FELV): Altamente recomendada para gatos com acesso ao exterior ou que convivem com outros gatos de status desconhecido. Reforço anual."
            )
        ),
        InformationItem(
            title = "Sinais de Alerta: Quando Procurar o Veterinário?",
            description = "Não hesite em buscar ajuda profissional se notar qualquer um destes sinais:",
            details = listOf(
                "Mudanças drásticas no apetite ou sede.",
                "Vômito ou diarreia persistentes.",
                "Dificuldade para respirar ou tosse intensa.",
                "Letargia extrema ou desorientação.",
                "Dor ao toque ou ao se movimentar.",
                "Sangramentos ou inchaços incomuns.",
                "Mudanças no comportamento (agressividade, isolamento)."
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Informações Úteis") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Um guia rápido para a saúde do seu pet!",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(infoList) { info ->
                InformationCard(info)
            }
        }
    }
}

@Composable
fun InformationCard(item: InformationItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium
            )
            if (item.details.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                item.details.forEach { detail ->
                    Text(
                        text = "• $detail",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
            }
        }
    }
}