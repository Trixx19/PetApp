// TELA DE INFORMAÇÕES / INFO NOVO
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
fun InformationScreen(navController: NavController) {
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
            title = "Vermífugos para Pets",
            description = "A vermifugação regular é crucial para a saúde do seu pet.",
            details = listOf(
                "Filhotes: A partir de 15 dias de vida, com repetições a cada 15-21 dias até 3-6 meses.",
                "Adultos: A cada 3 a 6 meses, ou conforme orientação veterinária.",
                "Importância: Previne doenças, melhora absorção de nutrientes e protege a família de zoonoses."
            )
        ),
        InformationItem(
            title = "Remédios Úteis para Ter em Casa (Emergências Leves)",
            description = "Alguns itens básicos para pequenos incidentes, sempre sob orientação veterinária.",
            details = listOf(
                "Antisséptico Tópico (Clorexidina): Para pequenos cortes e arranhões.",
                "Soro Fisiológico: Para limpeza de olhos e feridas.",
                "Gaze e Esparadrapo: Para curativos.",
                "Termômetro Retal: Para verificar a temperatura corporal.",
                "Probióticos Veterinários: Para diarreias leves (com prescrição)."
            )
        ),
        InformationItem(
            title = "Doenças Comuns em Cães e Sintomas",
            description = "Fique atento aos sinais que seu cão pode apresentar.",
            details = listOf(
                "Cinomose: Febre, secreção nasal e ocular, vômito, diarreia, tosse, convulsões, tiques nervosos.",
                "Parvovirose: Vômito e diarreia (com sangue) severos, perda de apetite, letargia, desidratação.",
                "Leptospirose: Febre, vômito, icterícia (pele e mucosas amareladas), dor muscular, insuficiência renal.",
                "Tosse dos Canis: Tosse seca e persistente, engasgos, febre leve."
            )
        ),
        InformationItem(
            title = "Doenças Comuns em Gatos e Sintomas",
            description = "Conheça as principais doenças que afetam os felinos.",
            details = listOf(
                "Rinotraqueíte Viral Felina (Herpesvírus): Espirros, secreção nasal e ocular, febre, perda de apetite, úlceras na boca.",
                "Calicivirose Felina: Úlceras na boca e língua, espirros, secreção nasal, claudicação (manqueira temporária).",
                "Panleucopenia Felina: Vômito, diarreia (com ou sem sangue), perda de apetite, letargia severa, desidratação.",
                "Leucemia Felina (FELV): Anemia, perda de peso, febre, infecções recorrentes, tumores. (Doença grave, vacinação é crucial)."
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
                title = { Text("Informações Pet") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar para Início"
                        )
                    }
                }
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
                    text = "Um guia para a saúde do seu pet!",
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
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}