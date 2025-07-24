// CLASSE DE TEMA
package com.example.petapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.petapp.ui.theme.ThemeVariant
import com.example.petapp.ui.theme.getLightColorScheme
import com.example.petapp.ui.theme.getDarkColorScheme

@Composable
fun PetAppTheme(
    darkTheme: Boolean = false,
    // MUDE AQUI para usar o tema azul como padrão
    themeVariant: ThemeVariant = ThemeVariant.BLUE_ACCENT,
    content: @Composable () -> Unit
) {
    // O resto do seu código continua igual...
    val colors = if (darkTheme) {
        getDarkColorScheme(themeVariant)
    } else {
        getLightColorScheme(themeVariant)
    }

    MaterialTheme(
        colorScheme = colors,
        typography = PetAppTypography,
        content = content
    )
}