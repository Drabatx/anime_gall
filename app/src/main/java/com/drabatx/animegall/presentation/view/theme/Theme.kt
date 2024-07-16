package com.drabatx.animegall.presentation.view.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val BaseColor = Color(0xFFCF5F69)
private val DarkColorScheme = darkColorScheme(
    primary = BaseColor,
    secondary = BaseColor.copy(alpha = 0.8f),
    tertiary = BaseColor.copy(alpha = 0.6f),
    onPrimary = Color.White, // Color de texto sobre el color primario
    onSecondary = Color.White, // Color de texto sobre el color secundario
    onTertiary = Color.White // Color de texto sobre el color terciario
)

private val LightColorScheme = lightColorScheme(
    primary = BaseColor.copy(alpha = 0.5f),
    secondary = BaseColor.copy(alpha = 0.3f),
    tertiary = BaseColor.copy(alpha = 0.1f),
    onPrimary = Color.Black, // Color de texto sobre el color primario
    onSecondary = Color.Black, // Color de texto sobre el color secundario
    onTertiary = Color.Black // Color de texto sobre el color terciario
)

@Composable
fun AnimeGallTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}