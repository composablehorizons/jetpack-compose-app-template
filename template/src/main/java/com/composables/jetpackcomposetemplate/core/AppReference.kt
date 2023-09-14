package com.composables.jetpackcomposetemplate.core

import android.app.Application

object AppReference {
    fun requireApplication(): Application {
        return requireNotNull(application) {
            "Tried to get application before instance was ready. Make sure that the ${AppContentProvider::class.simpleName} is not disable from your AndroidManifest.xml"
        }
    }

    var application: Application? = null
}