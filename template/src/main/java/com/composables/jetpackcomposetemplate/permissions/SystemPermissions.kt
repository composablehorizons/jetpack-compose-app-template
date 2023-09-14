package com.composables.jetpackcomposetemplate.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

interface SystemPermissions {
    fun hasPermission(camera: String): Boolean
}

internal class AndroidSystemPermissions(
    private val appContext: Context,
) : SystemPermissions {
    override fun hasPermission(camera: String): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext, camera
        ) == PackageManager.PERMISSION_GRANTED
    }
}