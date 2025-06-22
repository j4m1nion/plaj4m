package com.j4m1nion.j4player.player.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.j4m1nion.j4player.player.R

@Composable
fun PlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val darkColors = darkColorScheme(
        primary = Color(context.getColor(R.color.primary)),
        onPrimary = Color(context.getColor(R.color.secondary_dark)),
        secondary = Color(context.getColor(R.color.secondary)),
        primaryContainer = Color(context.getColor(R.color.secondary_light)),
        onSecondary = Color(context.getColor(R.color.primary)),
        secondaryContainer = Color(context.getColor(R.color.secondary_dark)),
        onSecondaryContainer = Color(context.getColor(R.color.primary)),
        background = Color(context.getColor(R.color.secondary_dark)),
        onBackground = Color(context.getColor(R.color.primary)),
        surfaceBright = Color.White,

    )

    val lightColors = lightColorScheme(
        primary = Color(context.getColor(R.color.primary)),
        onPrimary = Color(context.getColor(R.color.secondary_dark)),
        secondary = Color(context.getColor(R.color.secondary)),
        primaryContainer = Color(context.getColor(R.color.secondary_light)),
        onSecondary = Color(context.getColor(R.color.primary)),
        secondaryContainer = Color(context.getColor(R.color.secondary_dark)),
        onSecondaryContainer = Color(context.getColor(R.color.primary)),
        background = Color(context.getColor(R.color.secondary_dark)),
        onBackground = Color(context.getColor(R.color.primary)),
        surfaceBright = Color.White

    )

    val themedColors = when(darkTheme){
        true -> darkColors
        false -> lightColors
    }

    val robotoFamily = FontFamily(
        Font(R.font.roboto_black, FontWeight.Black),
        Font(R.font.roboto_black_italic, FontWeight.Black, FontStyle.Italic),
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_bold_italic, FontWeight.Black, FontStyle.Italic),
        Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_light_italic, FontWeight.Light, FontStyle.Italic),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_medium_italic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_thin, FontWeight.Thin),
        Font(R.font.roboto_thin_italic, FontWeight.Thin, FontStyle.Italic)
    )


    val typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Thin,
            fontSize = 14.sp
        ),
        bodySmall = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Thin,
            fontSize = 12.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W900,
            fontSize = 30.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W900,
            fontSize = 25.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W900,
            fontSize = 22.sp
        ),
        titleLarge = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W700,
            fontSize = 20.sp
        ),
        titleMedium = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W600,
            fontSize = 18.sp
        ),
        titleSmall = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W600,
            fontSize = 16.sp
        ),
        labelLarge = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W600,
            fontSize = 16.sp
        ),
        labelMedium = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W600,
            fontSize = 14.sp
        ),
        labelSmall = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W600,
            fontSize = 10.sp
        ),
        displayLarge = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W600,
            fontSize = 16.sp
        ),
        displayMedium = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W600,
            fontSize = 14.sp
        ),
        displaySmall = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.W600,
            fontSize = 12.sp
        )

    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(12.dp)
    )

    MaterialTheme(
        colorScheme = themedColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}