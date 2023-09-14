@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package co.composables.jetpackcomposetemplate

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun PermissionRequired(onDenyClick: () -> Unit, guardedContent: @Composable () -> Unit) {
    val state = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    Scaffold { padding ->
        when {
            state.status.isGranted -> guardedContent()
            else -> {
                LaunchedEffect(Unit) {
                    state.launchPermissionRequest()
                }
                PermissionRequiredScreen(
                    modifier = Modifier.padding(padding),
                    onDenyClick = onDenyClick
                )
            }
        }
    }
}

@Composable
fun PermissionRequiredScreen(modifier: Modifier, onDenyClick: () -> Unit) {
    Scaffold(
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity).widthSizeClass
        val centered = windowSizeClass == WindowWidthSizeClass.Expanded
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp, bottom = 12.dp),
                horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        Icons.Rounded.LocationOn,
                        contentDescription = "Icon",
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(Modifier.height(36.dp))
                Text(
                    text = "Really important permission is required",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(36.dp))
                Text(
                    text = "Well not really. This is used for demo purposes only to show you how a request permission screen works.",
                    textAlign = if (centered) TextAlign.Center else TextAlign.Start,
                )

                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = onDenyClick, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
                ) {
                    Text("Deny")
                }
            }
        }
    }
}