package com.rivaldofez.cubihub.viewmodel

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.rivaldofez.cubihub.database.DetailUserDatabase.Companion.CONTENT_URI
import com.rivaldofez.cubihub.helper.toContentValues
import com.rivaldofez.cubihub.helper.toDetailUser
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
    val listFavoriteUser = MutableLiveData<List<DetailUser>>()
    val favoriteUser = MutableLiveData<DetailUser>()

    fun getFavoriteUsers(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val cursor: Cursor? = context.contentResolver.query(CONTENT_URI, null,null, null, null)
            if (cursor != null) {
                listFavoriteUser.postValue(cursor.toListUser())
            }
        }
    }

    fun getFavoriteUserById(context: Context, id: String){
        val idUri = Uri.parse(CONTENT_URI.toString() + "/" + id)
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = context.contentResolver.query(idUri,null,null,null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                favoriteUser.postValue(cursor.toDetailUser())
            }
        }
    }

    fun insertUser(context: Context, detailUser: DetailUser){
        viewModelScope.launch(Dispatchers.IO) {
            context.contentResolver.insert(CONTENT_URI, detailUser.toContentValues())
        }
    }

    fun deleteUser(context: Context,id: String){
        val idUri = Uri.parse(CONTENT_URI.toString() + "/" + id)
        viewModelScope.launch(Dispatchers.IO) {
            context.contentResolver.delete(idUri,null,null)
            Log.d("Testong", "delete view model")
        }
    }

    fun initializeModel(context: Context){
        getFavoriteUsers(context)
    }

}