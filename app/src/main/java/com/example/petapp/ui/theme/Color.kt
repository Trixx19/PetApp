// CLASSE DE CORES
package com.example.petapp.ui.theme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.ColorScheme

// enum dos temas
enum class ThemeVariant(val displayName: String) {
    MONOCHROME("Monocromático"),
    GREEN_ACCENT("Verde"),
    BLUE_ACCENT("Azul"),
    RED_ACCENT("Vermelho"),
    PINK_ACCENT("Rosa"),
    PURPLE_ACCENT("Roxo")
}
// cores para o tema claro
val LightPrimary = Color(0xFF757575)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFE0E0E0)
val LightOnPrimaryContainer = Color(0xFF1A1A1A)

val LightSecondary = Color(0xFFBDBDBD)
val LightOnSecondary = Color(0xFF1A1A1A)
val LightSecondaryContainer = Color(0xFFF5F5F5)
val LightOnSecondaryContainer = Color(0xFF424242)

val LightTertiary = Color(0xFFE0E0E0)
val LightOnTertiary = Color(0xFF1A1A1A)
val LightTertiaryContainer = Color(0xFFF5F5F5)
val LightOnTertiaryContainer = Color(0xFF424242)

val LightBackground = Color(0xFFFFFFFF)
val LightOnBackground = Color(0xFF1A1A1A)
val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF1A1A1A)
val LightSurfaceVariant = Color(0xFFECEFF1)
val LightOnSurfaceVariant = Color(0xFF424242)

val LightError = Color(0xFFB00020)
val LightOnError = Color(0xFFFFFFFF)
val LightErrorContainer = Color(0xFFFCD8DC)
val LightOnErrorContainer = Color(0xFF66000C)
val LightOutline = Color(0xFFBDBDBD)

// vacina tomada
val SuccessGreen = Color(0xFF2E7D32)

// cores para o tema escuro
val DarkPrimary = Color(0xFFBDBDBD)
val DarkOnPrimary = Color(0xFF1A1A1A)
val DarkPrimaryContainer = Color(0xFF424242)
val DarkOnPrimaryContainer = Color(0xFFE0E0E0)

val DarkSecondary = Color(0xFF757575)
val DarkOnSecondary = Color(0xFFFFFFFF)
val DarkSecondaryContainer = Color(0xFF212121)
val DarkOnSecondaryContainer = Color(0xFFBDBDBD)

val DarkTertiary = Color(0xFF424242)
val DarkOnTertiary = Color(0xFFFFFFFF)
val DarkTertiaryContainer = Color(0xFF212121)
val DarkOnTertiaryContainer = Color(0xFFBDBDBD)

val DarkBackground = Color(0xFF0A0A0A)
val DarkOnBackground = Color(0xFFF8F8F8)
val DarkSurface = Color(0xFF1F1F1F)
val DarkOnSurface = Color(0xFFF8F8F8)
val DarkSurfaceVariant = Color(0xFF333333)
val DarkOnSurfaceVariant = Color(0xFFB0BEC5)

val DarkError = Color(0xFFCF6679)
val DarkOnError = Color(0xFF000000)
val DarkErrorContainer = Color(0xFF8C0000)
val DarkOnErrorContainer = Color(0xFFFFFFFF)
val DarkOutline = Color(0xFF757575)

// Paleta Azul
val BluePrimary = Color(0xFF0D47A1)
val BlueSecondary = Color(0xFF1976D2)
val BlueTertiary = Color(0xFF64B5F6)

// Paleta Verde
val GreenPrimary = Color(0xFF1B5E20)
val GreenSecondary = Color(0xFF388E3C)
val GreenTertiary = Color(0xFF81C784)

// Paleta Rosa
val PinkPrimary = Color(0xFF880E4F)
val PinkSecondary = Color(0xFFC2185B)
val PinkTertiary = Color(0xFFF06292)

// Fundo para a paleta Roxo
val PurpleBackground = Color(0xFFF3E5F5) // Um roxo bem claro

// Fundo para a paleta Azul
val BlueBackground = Color(0xFFE3F2FD) // Um azul bem claro

// Fundo para a paleta Verde
val GreenBackground = Color(0xFFE8F5E9) // Um verde bem claro

// Fundo para a paleta Rosa
val PinkBackground = Color(0xFFFCE4EC) // Um rosa bem claro

val DefaultThemeColor = Color(0xFFE0E0E0)
// cores dos lembretes
val ReminderGreen = Color(0xFF2E7D32)
val ReminderYellow = Color(0xFFFFEB3B)
val ReminderRed = Color(0xFFC62828)

// Tema Verde
val GreenLightPrimary = Color(0xFF4CAF50)
val GreenLightOnPrimary = Color(0xFFFFFFFF)
val GreenLightPrimaryContainer = Color(0xFFC8E6C9)
val GreenLightOnPrimaryContainer = Color(0xFF1B5E20)
val GreenLightBackground = Color(0xFFE8F5E9)
val GreenLightOnBackground = Color(0xFF1A1A1A)

val GreenDarkPrimary = Color(0xFF81C784)
val GreenDarkOnPrimary = Color(0xFF000000)
val GreenDarkPrimaryContainer = Color(0xFF388E3C)
val GreenDarkOnPrimaryContainer = Color(0xFFDCEDC8)
val GreenDarkBackground = Color(0xFF1A2A1A)
val GreenDarkOnBackground = Color(0xFFF8F8F8)

// Tema Azul
val BlueLightPrimary = Color(0xFF2196F3)
val BlueLightOnPrimary = Color(0xFFFFFFFF)
val BlueLightPrimaryContainer = Color(0xFFBBDEFB)
val BlueLightOnPrimaryContainer = Color(0xFF1565C0)
val BlueLightBackground = Color(0xFFE3F2FD)
val BlueLightOnBackground = Color(0xFF1A1A1A)

val BlueDarkPrimary = Color(0xFF90CAF9)
val BlueDarkOnPrimary = Color(0xFF000000)
val BlueDarkPrimaryContainer = Color(0xFF1976D2)
val BlueDarkOnPrimaryContainer = Color(0xFFE3F2FD)
val BlueDarkBackground = Color(0xFF0A1A2A)
val BlueDarkOnBackground = Color(0xFFF8F8F8)

// Tema Vermelho
val RedLightPrimary = Color(0xFFD32F2F)
val RedLightOnPrimary = Color(0xFFFFFFFF)
val RedLightPrimaryContainer = Color(0xFFFFCDD2)
val RedLightOnPrimaryContainer = Color(0xFFC62828)
val RedLightBackground = Color(0xFFFFF8F8)
val RedLightOnBackground = Color(0xFF1A1A1A)

val RedDarkPrimary = Color(0xFFEF5350)
val RedDarkOnPrimary = Color(0xFF000000)
val RedDarkPrimaryContainer = Color(0xFFB71C1C)
val RedDarkOnPrimaryContainer = Color(0xFFFFCDD2)
val RedDarkBackground = Color(0xFF2A0A0A)
val RedDarkOnBackground = Color(0xFFF8F8F8)

// Tema Rosa
val PinkLightPrimary = Color(0xFFC2185B)
val PinkLightOnPrimary = Color(0xFFFFFFFF)
val PinkLightPrimaryContainer = Color(0xFFF8BBD0)
val PinkLightOnPrimaryContainer = Color(0xFF880E4F)
val PinkLightBackground = Color(0xFFFCE4EC)
val PinkLightOnBackground = Color(0xFF1A1A1A)

val PinkDarkPrimary = Color(0xFFEC407A)
val PinkDarkOnPrimary = Color(0xFF000000)
val PinkDarkPrimaryContainer = Color(0xFFAD1457)
val PinkDarkOnPrimaryContainer = Color(0xFFFCE4EC)
val PinkDarkBackground = Color(0xFF3A0A1A)
val PinkDarkOnBackground = Color(0xFFF8F8F8)

// Tema Roxo
val PurpleLightPrimary = Color(0xFF7B1FA2)
val PurpleLightOnPrimary = Color(0xFFFFFFFF)
val PurpleLightPrimaryContainer = Color(0xFFE1BEE7)
val PurpleLightOnPrimaryContainer = Color(0xFF4A148C)
val PurpleLightBackground = Color(0xFFF3E5F5)
val PurpleLightOnBackground = Color(0xFF1A1A1A)

val PurpleDarkPrimary = Color(0xFFAB47BC)
val PurpleDarkOnPrimary = Color(0xFF000000)
val PurpleDarkPrimaryContainer = Color(0xFF6A1B9A)
val PurpleDarkOnPrimaryContainer = Color(0xFFF3E5F5)
val PurpleDarkBackground = Color(0xFF1A0A2A)
val PurpleDarkOnBackground = Color(0xFFF8F8F8)

// funções para obter o color do tema socrro deus

fun getLightColorScheme(themeVariant: ThemeVariant): ColorScheme {
    return when (themeVariant) {
        ThemeVariant.MONOCHROME -> lightColorScheme(
            primary = LightPrimary, onPrimary = LightOnPrimary, primaryContainer = LightPrimaryContainer, onPrimaryContainer = LightOnPrimaryContainer,
            secondary = LightSecondary, onSecondary = LightOnSecondary, secondaryContainer = LightSecondaryContainer, onSecondaryContainer = LightOnSecondaryContainer,
            tertiary = LightTertiary, onTertiary = LightOnTertiary, tertiaryContainer = LightTertiaryContainer, onTertiaryContainer = LightOnTertiaryContainer,
            background = LightBackground, onBackground = LightOnBackground, surface = LightSurface, onSurface = LightOnSurface,
            surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
            error = LightError, onError = LightOnError, errorContainer = LightErrorContainer, onErrorContainer = LightOnErrorContainer,
            outline = LightOutline
        )
        ThemeVariant.GREEN_ACCENT -> lightColorScheme(
            primary = GreenLightPrimary, onPrimary = GreenLightOnPrimary, primaryContainer = GreenLightPrimaryContainer, onPrimaryContainer = GreenLightOnPrimaryContainer,
            secondary = LightSecondary, onSecondary = LightOnSecondary, secondaryContainer = LightSecondaryContainer, onSecondaryContainer = LightOnSecondaryContainer,
            tertiary = LightTertiary, onTertiary = LightOnTertiary, tertiaryContainer = LightTertiaryContainer, onTertiaryContainer = LightOnTertiaryContainer,
            background = GreenLightBackground, onBackground = GreenLightOnBackground,
            surface = LightSurface, onSurface = LightOnSurface,
            surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
            error = LightError, onError = LightOnError, errorContainer = LightErrorContainer, onErrorContainer = LightOnSecondaryContainer,
            outline = LightOutline
        )
        ThemeVariant.BLUE_ACCENT -> lightColorScheme(
            primary = BlueLightPrimary, onPrimary = BlueLightOnPrimary, primaryContainer = BlueLightPrimaryContainer, onPrimaryContainer = BlueLightOnPrimaryContainer,
            secondary = LightSecondary, onSecondary = LightOnSecondary, secondaryContainer = LightSecondaryContainer, onSecondaryContainer = LightOnSecondaryContainer,
            tertiary = LightTertiary, onTertiary = LightOnTertiary, tertiaryContainer = LightTertiaryContainer, onTertiaryContainer = LightOnTertiaryContainer,
            background = BlueLightBackground, onBackground = BlueLightOnBackground,
            surface = LightSurface, onSurface = LightOnSurface,
            surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
            error = LightError, onError = LightOnError, errorContainer = LightErrorContainer, onErrorContainer = LightOnSecondaryContainer,
            outline = LightOutline
        )
        ThemeVariant.RED_ACCENT -> lightColorScheme(
            primary = RedLightPrimary, onPrimary = RedLightOnPrimary, primaryContainer = RedLightPrimaryContainer, onPrimaryContainer = RedLightOnPrimaryContainer,
            secondary = LightSecondary, onSecondary = LightOnSecondary, secondaryContainer = LightSecondaryContainer, onSecondaryContainer = LightOnSecondaryContainer,
            tertiary = LightTertiary, onTertiary = LightOnTertiary, tertiaryContainer = LightTertiaryContainer, onTertiaryContainer = LightOnTertiaryContainer,
            background = RedLightBackground, onBackground = RedLightOnBackground,
            surface = LightSurface, onSurface = LightOnSurface,
            surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
            error = LightError, onError = LightOnError, errorContainer = LightErrorContainer, onErrorContainer = LightOnSecondaryContainer,
            outline = LightOutline
        )
        ThemeVariant.PINK_ACCENT -> lightColorScheme(
            primary = PinkLightPrimary, onPrimary = PinkLightOnPrimary, primaryContainer = PinkLightPrimaryContainer, onPrimaryContainer = PinkLightOnPrimaryContainer,
            secondary = LightSecondary, onSecondary = LightOnSecondary, secondaryContainer = LightSecondaryContainer, onSecondaryContainer = LightOnSecondaryContainer,
            tertiary = LightTertiary, onTertiary = LightOnTertiary, tertiaryContainer = LightTertiaryContainer, onTertiaryContainer = LightOnTertiaryContainer,
            background = PinkLightBackground, onBackground = PinkLightOnBackground,
            surface = LightSurface, onSurface = LightOnSurface,
            surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
            error = LightError, onError = LightOnError, errorContainer = LightErrorContainer, onErrorContainer = LightOnSecondaryContainer,
            outline = LightOutline
        )
        ThemeVariant.PURPLE_ACCENT -> lightColorScheme(
            primary = PurpleLightPrimary, onPrimary = PurpleLightOnPrimary, primaryContainer = PurpleLightPrimaryContainer, onPrimaryContainer = PurpleLightOnPrimaryContainer,
            secondary = LightSecondary, onSecondary = LightOnSecondary, secondaryContainer = LightSecondaryContainer, onSecondaryContainer = LightOnSecondaryContainer,
            tertiary = LightTertiary, onTertiary = LightOnTertiary, tertiaryContainer = LightTertiaryContainer, onTertiaryContainer = LightOnTertiaryContainer,
            background = PurpleLightBackground, onBackground = PurpleLightOnBackground,
            surface = LightSurface, onSurface = LightOnSurface,
            surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
            error = LightError, onError = LightOnError, errorContainer = LightErrorContainer, onErrorContainer = LightOnSecondaryContainer,
            outline = LightOutline
        )
    }
}

fun getDarkColorScheme(themeVariant: ThemeVariant): ColorScheme {
    return when (themeVariant) {
        ThemeVariant.MONOCHROME -> darkColorScheme(
            primary = DarkPrimary, onPrimary = DarkOnPrimary, primaryContainer = DarkPrimaryContainer, onPrimaryContainer = DarkOnPrimaryContainer,
            secondary = DarkSecondary, onSecondary = DarkOnSecondary, secondaryContainer = DarkSecondaryContainer, onSecondaryContainer = DarkOnSecondaryContainer,
            tertiary = DarkTertiary, onTertiary = DarkOnTertiary, tertiaryContainer = DarkTertiaryContainer, onTertiaryContainer = DarkOnTertiaryContainer,
            background = DarkBackground, onBackground = DarkOnBackground, surface = DarkSurface, onSurface = DarkOnSurface,
            surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
            error = DarkError, onError = DarkOnError, errorContainer = DarkErrorContainer, onErrorContainer = DarkErrorContainer,
            outline = DarkOutline
        )
        ThemeVariant.GREEN_ACCENT -> darkColorScheme(
            primary = GreenDarkPrimary, onPrimary = GreenDarkOnPrimary, primaryContainer = GreenDarkPrimaryContainer, onPrimaryContainer = GreenDarkOnPrimaryContainer,
            secondary = DarkSecondary, onSecondary = DarkOnSecondary, secondaryContainer = DarkSecondaryContainer, onSecondaryContainer = DarkOnSecondaryContainer,
            tertiary = DarkTertiary, onTertiary = DarkOnTertiary, tertiaryContainer = DarkTertiaryContainer, onTertiaryContainer = DarkOnTertiaryContainer,
            background = GreenDarkBackground, onBackground = GreenDarkOnBackground,
            surface = DarkSurface, onSurface = DarkOnSurface,
            surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
            error = DarkError, onError = DarkOnError, errorContainer = DarkErrorContainer, onErrorContainer = DarkErrorContainer,
            outline = DarkOutline
        )
        ThemeVariant.BLUE_ACCENT -> darkColorScheme(
            primary = BlueDarkPrimary, onPrimary = BlueDarkOnPrimary, primaryContainer = BlueDarkPrimaryContainer, onPrimaryContainer = BlueDarkOnPrimaryContainer,
            secondary = DarkSecondary, onSecondary = DarkOnSecondary, secondaryContainer = DarkSecondaryContainer, onSecondaryContainer = DarkOnSecondaryContainer,
            tertiary = DarkTertiary, onTertiary = DarkOnTertiary, tertiaryContainer = DarkTertiaryContainer, onTertiaryContainer = DarkOnTertiaryContainer,
            background = BlueDarkBackground, onBackground = BlueDarkOnBackground,
            surface = DarkSurface, onSurface = DarkOnSurface,
            surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
            error = DarkError, onError = DarkOnError, errorContainer = DarkErrorContainer, onErrorContainer = DarkErrorContainer,
            outline = DarkOutline
        )
        ThemeVariant.RED_ACCENT -> darkColorScheme(
            primary = RedDarkPrimary, onPrimary = RedDarkOnPrimary, primaryContainer = RedDarkPrimaryContainer, onPrimaryContainer = RedDarkOnPrimaryContainer,
            secondary = DarkSecondary, onSecondary = DarkOnSecondary, secondaryContainer = DarkSecondaryContainer, onSecondaryContainer = DarkOnSecondaryContainer,
            tertiary = DarkTertiary, onTertiary = DarkOnTertiary, tertiaryContainer = DarkTertiaryContainer, onTertiaryContainer = DarkOnTertiaryContainer,
            background = RedDarkBackground, onBackground = RedDarkOnBackground,
            surface = DarkSurface, onSurface = DarkOnSurface,
            surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
            error = DarkError, onError = DarkOnError, errorContainer = DarkErrorContainer, onErrorContainer = DarkErrorContainer,
            outline = DarkOutline
        )
        ThemeVariant.PINK_ACCENT -> darkColorScheme(
            primary = PinkDarkPrimary, onPrimary = PinkDarkOnPrimary, primaryContainer = PinkDarkPrimaryContainer, onPrimaryContainer = PinkDarkOnPrimaryContainer,
            secondary = DarkSecondary, onSecondary = DarkOnSecondary, secondaryContainer = DarkSecondaryContainer, onSecondaryContainer = DarkOnSecondaryContainer,
            tertiary = DarkTertiary, onTertiary = DarkOnTertiary, tertiaryContainer = DarkTertiaryContainer, onTertiaryContainer = DarkOnTertiaryContainer,
            background = PinkDarkBackground, onBackground = PinkDarkOnBackground,
            surface = DarkSurface, onSurface = DarkOnSurface,
            surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
            error = DarkError, onError = DarkOnError, errorContainer = DarkErrorContainer, onErrorContainer = DarkErrorContainer,
            outline = DarkOutline
        )
        ThemeVariant.PURPLE_ACCENT -> darkColorScheme(
            primary = PurpleDarkPrimary, onPrimary = PurpleDarkOnPrimary, primaryContainer = PurpleDarkPrimaryContainer, onPrimaryContainer = PurpleDarkOnPrimaryContainer,
            secondary = DarkSecondary, onSecondary = DarkOnSecondary, secondaryContainer = DarkSecondaryContainer, onSecondaryContainer = DarkOnSecondaryContainer,
            tertiary = DarkTertiary, onTertiary = DarkOnTertiary, tertiaryContainer = DarkTertiaryContainer, onTertiaryContainer = DarkOnTertiaryContainer,
            background = PurpleDarkBackground, onBackground = PurpleDarkOnBackground,
            surface = DarkSurface, onSurface = DarkOnSurface,
            surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
            error = DarkError, onError = DarkOnError, errorContainer = DarkErrorContainer, onErrorContainer = DarkErrorContainer,
            outline = DarkOutline
        )
    }
}