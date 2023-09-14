package com.composables.jetpackcomposetemplate.permissions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.composables.jetpackcomposetemplate.core.findActivity
import kotlinx.coroutines.launch

@Composable
fun rememberRequestOptionalPermissionLauncher(
    permission: String,
    snackbarHostState: SnackbarHostState,
    onGranted: () -> Unit,
): RequestOptionalPermissionLauncher {
    val launcher = rememberLauncherForActivityResult(RequestPermission(), onResult = { isGranted ->
        if (isGranted) {
            onGranted()
        }
    })
    val activity = requireNotNull(LocalContext.current.findActivity()) {
        "You tried to use rememberRequestOptionalPermissionLauncher() from a composable that is not linked to an Activity." +
                " An activity is required to use permissions"
    }
    val scope = rememberCoroutineScope()

    return remember(snackbarHostState, activity) {
        RequestOptionalPermissionLauncher(
            onLaunch = {
                when {
                    hasPermission(permission, activity) -> onGranted()
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Permission required",
                                actionLabel = "Go to settings",
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", activity.packageName, null)
                                )
                                activity.startActivity(intent)
                            }
                        }
                    }
                    else -> {
                        launcher.launch(permission)
                    }
                }
            })
    }
}

private fun hasPermission(permission: String, appContext: Context) =
    ContextCompat.checkSelfPermission(
        appContext, permission) == PackageManager.PERMISSION_GRANTED

class RequestOptionalPermissionLauncher(
    private val onLaunch: () -> Unit,
) {
    fun launch() {
        onLaunch()
    }
}
