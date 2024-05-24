package com.example.reviewerwriter.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkBrown20,
    onPrimary = DarkBrown60,
    primaryContainer = Brown30,
    onPrimaryContainer = Brown90,
    inversePrimary = DarkBrown80,
    secondary = DarkBrown70 ,
    onSecondary = DarkBrown20,
    secondaryContainer = Gray40,
    onSecondaryContainer = DarkBrown20,
    tertiary = Gray80,
    tertiaryContainer = Gray50,
    onTertiary = Gray30,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkBrown30,
    onBackground = Gray60,
    surface = DarkBrown20,
    onSurface = LightBrown80,
    inverseSurface = Gray50,
    inverseOnSurface = Gray50,
    surfaceVariant = LightBrown40,
    onSurfaceVariant = LightBrown70,
    outline = LightBrown80
)

private val LightColorScheme = lightColorScheme(
    primary = Brown30,
    onPrimary = LightBrown70,
    primaryContainer = Brown20,
    onPrimaryContainer = Brown90,
    inversePrimary = Brown80,
    secondary = DarkBrown40 ,
    onSecondary = Gray50,
    secondaryContainer = DarkBrown90,
    onSecondaryContainer = DarkBrown30,
    tertiary = Gray40,
    tertiaryContainer = Gray70,
    onTertiary = Gray20,
    error = Red40,
    onError = Gray50,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = LightBrown60,
    onBackground = LightBrown20,
    surface = LightBrown50,
    onSurface = Brown10,
    inverseSurface = LightBrown10,
    inverseOnSurface = LightBrown50,
    surfaceVariant = LightBrown70,
    onSurfaceVariant = LightBrown20,
    outline = Brown50
)
@Composable
fun ReviewerWriterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+

    // пока без динамических цветов
    dynamicColor: Boolean = false,
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
/*@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    val navController = rememberNavController()
    LoginView(context = LocalContext.current,navController )

}*/
