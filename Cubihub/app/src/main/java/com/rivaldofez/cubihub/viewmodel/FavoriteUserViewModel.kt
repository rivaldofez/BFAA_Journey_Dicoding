package com.rivaldofez.cubihub.viewmodel

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.rivaldofez.cubihub.database.DetailUserDatabase.Companion.CONTENT_URI
import com.rivaldofez.cubihub.helper.toContentValues
import com.rivaldofez.cubihub.helper.toListUser
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.model.UserList
import com.rivaldofez.cubihub.repository.FollowRepository
import com.rivaldofez.cubihub.repository.SearchUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteUserViewModel: ViewModel() {
//    lateinit var listFavoriteUser : MutableLiveData<List<DetailUser>>

    fun getFavoriteUsers(context: Context){
        Log.d("Tesmin", "get")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Tesmin", "launch")
            val cursor: Cursor? = context.contentResolver.query(CONTENT_URI, null,null, null, null)
            if (cursor != null) {
                Log.d("Teston", "getggfgfgf" + cursor.toString())
//                listFavoriteUser.value = cursor.toListUser()
            }
        }
    }

    fun insertUsers(context: Context, detailUser: DetailUser){
        viewModelScope.launch(Dispatchers.IO) {
            context.contentResolver.insert(CONTENT_URI, detailUser.toContentValues())
        }
    }

    fun initializeModel(context: Context){
        getFavoriteUsers(context)
//        listFavoriteUser = MutableLiveData<List<DetailUser>>()
    }
}