package com.rivaldofez.cubihub.database

import android.net.Uri

object DetailDatabaseUser {
        const val TABLE_NAME = "favorite_user"
        const val AUTHORITY = "com.rivaldofez.cubihub"
        const val SCHEME = "content"

        // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
}