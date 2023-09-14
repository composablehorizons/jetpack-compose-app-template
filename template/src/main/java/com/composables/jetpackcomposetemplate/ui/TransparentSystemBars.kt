package com.composables.jetpackcomposetemplate.ui

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TransparentSystemBars(
    colors: ColorScheme,
    useDarkIcons: Boolean,
) {
    val systemUiController = rememberSystemUiController()

    val color = colors.primaryContainer

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(color = Color.Transparent,
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color,
            darkIcons = useDarkIcons,
            navigationBarContrastEnforced = false
        )

        onDispose {}
    }
}
