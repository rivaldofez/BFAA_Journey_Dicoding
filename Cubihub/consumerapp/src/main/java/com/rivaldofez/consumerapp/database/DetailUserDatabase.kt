package com.rivaldofez.consumerapp.database

import android.net.Uri

object DetailDatabaseUser {
        private const val TABLE_NAME = "favorite_user"
        private const val AUTHORITY = "com.rivaldofez.cubihub"
        private const val SCHEME = "content"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
}