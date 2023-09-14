package com.composables.jetpackcomposetemplate

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.ChecksSdkIntAtLeast

private const val TAG = "Composables"
private var enabled = BuildConfig.DEBUG

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.BASE_1_1)
@SuppressLint("ObsoleteSdkInt")
// checking the SDK to see if we are running on Android or not.
// when running on android the SDK_INT will always be greater than BASE_1_1
// running on a computer, the SDK_INT will always be 0
private val isAndroid = Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE_1_1

fun setLogging(enabled: Boolean) {
    com.composables.jetpackcomposetemplate.enabled = enabled
}

fun debugLn(message: () -> Any) {
    if (enabled) {
        if (isAndroid) {
            Log.d(TAG, message().toString())
        } else {
            println(message().toString())
        }
    }
}

fun errorln(ex: Throwable) {
    if (enabled) {
        if (isAndroid) {
            Log.e(TAG, null, ex)
        } else {
            ex.printStackTrace()
        }
    }
}

fun errorln(message: () -> Any) {
    if (enabled) {
        if (isAndroid) {
            Log.e(TAG, message().toString())
        } else {
            System.err.println(message().toString())
        }
    }
}


fun verboseLn(message: () -> Any) {
    if (enabled) {
        if (isAndroid) {
            Log.v(TAG, message().toString())
        } else {
            println(message().toString())
        }
    }
}

fun warnln(message: () -> Any) {
    if (enabled) {
        if (isAndroid) {
            Log.w(TAG, message().toString())
        } else {
            println(message().toString())
        }
    }
}
