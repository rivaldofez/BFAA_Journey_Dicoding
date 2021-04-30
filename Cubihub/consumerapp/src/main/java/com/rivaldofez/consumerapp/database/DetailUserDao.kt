package com.rivaldofez.cubihub.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rivaldofez.cubihub.model.DetailUser

@Dao
interface DetailUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(detailuser: DetailUser): Long

    @Query("DELETE from favorite_user WHERE id = :idUser")
    fun deleteUser(idUser: Int): Int

    @Query("SELECT * FROM favorite_user ORDER BY id ASC")
    fun getUsersData() : Cursor

    @Query("SELECT * FROM favorite_user WHERE id = :idUser")
    fun getUserById(idUser: Int) : Cursor

}