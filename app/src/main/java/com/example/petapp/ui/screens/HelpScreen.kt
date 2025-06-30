// TELA DE AJUDA / FAQ
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
// modelo de dados para representar os itens do FAQ com perguntas e respostas
data class FAQItem(val question: String, val answer: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavController) {
    val faqList = listOf( // lista de perguntas frequentes e suas respostas
        FAQItem(
            question = "Como navegar entre as telas principais?",
            answer = "Use a barra de navegação inferior para acessar rapidamente as seções 'Início', 'Favoritos' e 'Informações'. O ícone da seção selecionada pulsará para indicar a tela atual."
        ),
        FAQItem(
            question = "O que encontro na tela 'Informações'?",
            answer = "Nessa tela, você encontrará um guia rápido com informações úteis sobre vacinas essenciais para cães e gatos, vermífugos, remédios básicos para emergências leves e sinais de alerta de doenças comuns para identificar problemas no seu pet."
        ),
        FAQItem(
            question = "Como cadastrar meu pet?",
            answer = "Para cadastrar um novo pet, vá até a tela 'Início' e clique no botão flutuante de '+' (Adicionar Novo Pet). Uma barra de progresso no topo da tela indicará o seu progresso no preenchimento dos campos."
        ),
        FAQItem(
            question = "O que significam as cores dos lembretes?",
            answer = "Na tela de detalhes do pet, os lembretes são coloridos para indicar a prioridade: Vermelho para 'Alta Prioridade', Amarelo para 'Média Prioridade' e Verde para 'Baixa Prioridade'."
        ),
        FAQItem(
            question = "Como funcionam as notificações?",
            answer = "Você pode agendar lembretes para seu pet com prioridade alta, média ou baixa. Notificações de Alta e Média prioridade emitirão um som especial para alertar você sobre eventos importantes."
        ),
        FAQItem(
            question = "O que são as mensagens que aparecem na parte inferior da tela?",
            answer = "Essas são Toasts de feedback que aparecem brevemente para informar sobre o sucesso de uma ação (ex: 'Pet salvo com sucesso!') ou avisos (ex: 'Nome e Raça são obrigatórios!')."
        ),
        FAQItem(
            question = "Como marcar vacinas como concluídas?",
            answer = "Na tela de detalhes do pet, você pode marcar uma vacina como concluída clicando no checkbox correspondente (se disponível) ou verificando o status de cada item na lista."
        ),
        FAQItem(
            question = "Como alterar o tema do aplicativo?",
            answer = "Vá até as 'Configurações' através do menu de três pontinhos na TopAppBar ou da barra de navegação inferior e ative ou desative o modo escuro."
        ),
        FAQItem(
            question = "Posso adicionar mais de um pet?",
            answer = "Sim! O aplicativo permite que você adicione quantos pets quiser, cada um com seu próprio controle de vacinas, consultas e lembretes."
        ),
        FAQItem(
            question = "O que são os favoritos?",
            answer = "Os favoritos permitem que você tenha acesso rápido aos pets que você mais acompanha. Marque um pet como favorito na tela de detalhes dele ou na lista da tela inicial, clicando no ícone de coração."
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
                    onClick = {}, // implementar envio de mensagem futuramente, com banco de dados
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
