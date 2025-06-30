package com.example.petapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

enum class ColorPalette {
    DEFAULT,PURPLE, BLUE, GREEN, PINK
}

// --- TEMA CLARO PADRÃO ---
private val DefaultColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = LightBackground // Usando a cor de fundo padrão
)

// --- TEMAS CLAROS ---
private val PurpleColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = PurpleBackground
)
private val BlueColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    tertiary = BlueTertiary,
    background = BlueBackground
)
private val GreenColorScheme = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = GreenTertiary,
    background = GreenBackground
)
private val PinkColorScheme = lightColorScheme(
    primary = PinkPrimary,
    secondary = PinkSecondary,
    tertiary = PinkTertiary,
    background = PinkBackground
)

// --- TEMA ESCURO ---
private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    tertiary = DarkTertiary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
)


@Composable
fun PetAppTheme(
    darkTheme: Boolean = false,
    colorPalette: ColorPalette = ColorPalette.PURPLE,
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        when (colorPalette) {
            ColorPalette.DEFAULT -> DefaultColorScheme
            ColorPalette.PURPLE -> PurpleColorScheme
            ColorPalette.BLUE -> BlueColorScheme
            ColorPalette.GREEN -> GreenColorScheme
            ColorPalette.PINK -> PinkColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = PetAppTypography,
        content = content
    )
}