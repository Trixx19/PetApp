package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class FAQItem(val question: String, val answer: String) // modelo de dados para representar os itens do FAQ

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavController) {
    val faqList = listOf( // lista de perguntas frequentes e suas respostas
        FAQItem(
            question = "Como cadastrar meu pet?",
            answer = "Para cadastrar seu pet, vá até a tela inicial e clique no botão de adicionar pet."
        ),
        FAQItem(
            question = "Como marcar vacinas como concluídas?",
            answer = "Na tela de detalhes do pet, marque a vacina como concluída clicando no checkbox correspondente."
        ),
        FAQItem(
            question = "O que acontece se excluir um pet?",
            answer = "Excluir um pet remove todas as suas informações e lembretes do aplicativo."
        ),
        FAQItem(
            question = "Como alterar o tema do aplicativo?",
            answer = "Vá até as Configurações através da barra inferior ou menu e ative ou desative o modo escuro."
        ),
        FAQItem(
            question = "Posso adicionar mais de um pet?",
            answer = "Sim! O aplicativo permite que você adicione quantos pets quiser, cada um com seu próprio controle de vacinas, consultas e lembretes."
        ),
        FAQItem(
            question = "O que são os favoritos?",
            answer = "Os favoritos permitem que você tenha acesso rápido aos pets que você mais acompanha. Marque um pet como favorito na tela de detalhes dele."
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajuda / FAQ") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(faqList) { faq ->
                FAQCard(faq)
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Button(
                    onClick = {}, // implementar envio de mensagem futuramente
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enviar Mensagem")
                }
            }
        }
    }
}

@Composable
fun FAQCard(faq: FAQItem) { // composable para exibir os itens de FAQ como cartões
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = faq.question,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = faq.answer,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
