package com.rivaldofez.cubihub.database

import android.content.Context
import android.net.Uri
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rivaldofez.cubihub.model.DetailUser

@Database(entities = [DetailUser::class], version = 1, exportSchema = false)
abstract class DetailUserDatabase : RoomDatabase() {
    abstract fun detailUserDao(): DetailUserDao

    companion object {
        const val TABLE_NAME = "favorite_user"
        const val AUTHORITY = "com.rivaldofez.cubihub"
        private const val SCHEME = "content"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        @Volatile
        private var INSTANCE: DetailUserDatabase? = null

        fun getDatabase(context: Context): DetailUserDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DetailUserDatabase::class.java,
                    TABLE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}