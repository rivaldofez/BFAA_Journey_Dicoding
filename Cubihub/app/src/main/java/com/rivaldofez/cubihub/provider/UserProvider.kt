package com.rivaldofez.cubihub.provider

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.rivaldofez.cubihub.database.DetailUserDao
import com.rivaldofez.cubihub.database.DetailUserDatabase
import com.rivaldofez.cubihub.database.DetailUserDatabase.Companion.AUTHORITY
import com.rivaldofez.cubihub.database.DetailUserDatabase.Companion.CONTENT_URI
import com.rivaldofez.cubihub.database.DetailUserDatabase.Companion.TABLE_NAME
import com.rivaldofez.cubihub.helper.toUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserProvider() : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var detailUserDao: DetailUserDao
    }

    init {
        // content://com.rivaldofez.cubihub/user
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
        // content://com.rivaldofez.cubihub/user/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER_ID)
    }

    override fun onCreate(): Boolean {
        detailUserDao = DetailUserDatabase.getDatabase(context!!.applicationContext).detailUserDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(sUriMatcher.match(uri)){
            USER -> detailUserDao.getUsersData()
            USER_ID -> uri.lastPathSegment?.toInt()?.let { detailUserDao.getUserById(it) }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        Log.d("Testin","start insert")
//        values?.toUserEntity()?.let {
//            Log.d("Testin","object" + it.toString())
//            GlobalScope.launch(Dispatchers.Default) {
//                detailUserDao.addUser(
//                    it
//                )
//            }
//        }
        val added: Long = when (USER) {
            sUriMatcher.match(uri) -> values?.toUserEntity()?.let {
                Log.d("Testin","object" + it.toString())
                detailUserDao.addUser(
                    it
                )
            } ?: 0
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        Log.d("Testin","dones insert")
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
       return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USER_ID) {
            sUriMatcher.match(uri) -> uri.lastPathSegment?.toInt()?.let {
                detailUserDao.deleteUser(
                    it
                )
            } ?: 0
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

}