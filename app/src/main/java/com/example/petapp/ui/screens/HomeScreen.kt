// TELA PRINCIPAL
package com.example.petapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.R
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Pet
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.mineicon),
                            contentDescription = "Ícone do Pet App",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pet App")
                    }
                },
                actions = {
                    MoreOptionsMenu(navController)
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_pet") }) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Novo Pet")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar pet") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(50),
                        clip = false
                    ),
                singleLine = true,
                shape = RoundedCornerShape(50),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Ícone de Busca",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = Color(0xFFE0E0E0),
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            val filteredPets = PetRepository.petList.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
            PetList(
                pets = filteredPets,
                navController = navController
            )
        }
    }
}
@Composable
fun PetList(pets: List<Pet>, navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(pets) { pet ->
            PetCard(pet = pet) {
                navController.navigate("details/${pet.id}")
            }
        }
    }
}
@Composable
fun PetCard(pet: Pet, onClick: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 200),
        label = "FavoriteIconScale"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = pet.imageRes),
                contentDescription = pet.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = pet.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = pet.specie,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Raça: ${pet.breed}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Sexo: ${pet.sex}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = pet.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(
                onClick = {
                    PetRepository.toggleFavorite(pet.id)
                    scale = 1.2f
                },
                modifier = Modifier.scale(animatedScale)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoritar",
                    tint = if (pet.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            LaunchedEffect(animatedScale) {
                if (animatedScale == 1.2f) {
                    scale = 1f
                }
            }
        }
    }
}

@Composable
fun MoreOptionsMenu(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Mais opções"
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Favoritos") },
            onClick = {
                expanded = false
                navController.navigate("favorites")
            }
        )
        DropdownMenuItem(
            text = { Text("Configurações") },
            onClick = {
                expanded = false
                navController.navigate("settings")
            }
        )
        DropdownMenuItem(
            text = { Text("Ajuda") },
            onClick = {
                expanded = false
                navController.navigate("help")
            }
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        val homeIconScale by animateFloatAsState( // animação de "pulsar"
            targetValue = if (currentRoute == "home") 1.2f else 1f,
            animationSpec = tween(durationMillis = 200),
            label = "HomeIconScale"
        )
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.navigate("home") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Início",
                    modifier = Modifier.scale(homeIconScale)
                )
            },
            label = { Text("Início") }
        )
        // Item "Favoritos"
        val favoritesIconScale by animateFloatAsState(
            targetValue = if (currentRoute == "favorites") 1.2f else 1f,
            animationSpec = tween(durationMillis = 200),
            label = "FavoritesIconScale"
        )
        NavigationBarItem(
            selected = currentRoute == "favorites",
            onClick = {
                if (currentRoute != "favorites") {
                    navController.navigate("favorites") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoritos",
                    modifier = Modifier.scale(favoritesIconScale)
                )
            },
            label = { Text("Favoritos") }
        )
        // Item "Informações"
        val infoIconScale by animateFloatAsState(
            targetValue = if (currentRoute == "information") 1.3f else 1f,
            animationSpec = tween(durationMillis = 300),
            label = "InfoIconScale"
        )
        NavigationBarItem(
            selected = currentRoute == "information",
            onClick = {
                if (currentRoute != "information") {
                    navController.navigate("information") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "Informações",
                    modifier = Modifier.scale(infoIconScale) // animação de escala
                )
            },
            label = { Text("Info") }
        )
    }
}