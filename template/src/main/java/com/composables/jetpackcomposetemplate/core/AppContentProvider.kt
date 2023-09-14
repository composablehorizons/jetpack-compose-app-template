package com.composables.jetpackcomposetemplate.core

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.composables.jetpackcomposetemplate.BuildConfig
import com.composables.jetpackcomposetemplate.setLogging

class AppContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        setLogging(BuildConfig.DEBUG)
        AppReference.application = requireNotNull(context) as Application
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        return -1
    }
}
