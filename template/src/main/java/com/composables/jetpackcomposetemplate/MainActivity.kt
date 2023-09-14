package com.composables.jetpackcomposetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.composables.blaze.sample.ui.theme.AppTheme
import com.composables.jetpackcomposetemplate.PermissionRequired
import com.ramcosta.composedestinations.DestinationsNavHost


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                PermissionRequired(
                    onDenyClick = { finish() },
                    guardedContent = { DestinationsNavHost(navGraph = NavGraphs.root) }
                )
            }
        }
    }
}
