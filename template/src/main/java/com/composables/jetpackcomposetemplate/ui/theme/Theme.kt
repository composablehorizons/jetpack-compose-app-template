package com.composables.blaze.sample.ui.theme

import android.content.pm.ActivityInfo
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.composables.jetpackcomposetemplate.LockScreenOrientation
import com.composables.jetpackcomposetemplate.ui.TransparentSystemBars

private val LightColorPalette = lightColorScheme(
    primary = Purple500,
    secondary = Teal200
)

@Composable
fun AppTheme(
    edgeToEdge: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colors = LightColorPalette
    if (edgeToEdge) {
        TransparentSystemBars(
            colors,
            // currently the sample app does not support dark theme
            // because of this, we want the icons to be dark (as the bars will be light)
            useDarkIcons = true
        )
    }
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
