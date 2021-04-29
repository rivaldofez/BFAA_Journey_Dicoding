package com.rivaldofez.cubihub.viewmodel

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.rivaldofez.cubihub.database.DetailUserDatabase.Companion.CONTENT_URI
import com.rivaldofez.cubihub.helper.toContentValues
import com.rivaldofez.cubihub.helper.toDetailUser
import com.rivaldofez.cubihub.helper.toListUser
import com.rivaldofez.cubihub.model.DetailUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteUserViewModel: ViewModel() {
    val listFavoriteUser = MutableLiveData<List<DetailUser>>()
    val favoriteUser = MutableLiveData<DetailUser>()
    val isFavoriteUser = MutableLiveData<Boolean>()
    val showProgress = MutableLiveData<Boolean>()

    fun getFavoriteUsers(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            showProgress.postValue(true)
            val cursor: Cursor? = context.contentResolver.query(CONTENT_URI, null,null, null, null)
            if (cursor != null) {
                listFavoriteUser.postValue(cursor.toListUser())
            }
            showProgress.postValue(false)
        }
    }

    fun getFavoriteUserById(context: Context, id: String){
        val idUri = Uri.parse(CONTENT_URI.toString() + "/" + id)
        viewModelScope.launch(Dispatchers.IO) {
            showProgress.postValue(true)
            val cursor = context.contentResolver.query(idUri,null,null,null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                favoriteUser.postValue(cursor.toDetailUser())
            }
            showProgress.postValue(false)
        }
    }

    fun checkFavoriteUser(context: Context, id: Int){
        showProgress.postValue(true)
        val idUri = Uri.parse(CONTENT_URI.toString() + "/" + id)
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = context.contentResolver.query(idUri,null,null,null, null)
            if (cursor != null) {
                if (cursor.moveToFirst() && cursor.count > 0) {
                    isFavoriteUser.postValue(true)
                }else{
                    isFavoriteUser.postValue(false)
                }
            }
        }
        showProgress.postValue(false)
    }

    fun insertUser(context: Context, detailUser: DetailUser){
        viewModelScope.launch(Dispatchers.IO) {
            showProgress.postValue(true)
            context.contentResolver.insert(CONTENT_URI, detailUser.toContentValues())
            showProgress.postValue(false)
        }
    }

    fun deleteUser(context: Context, id: Int){
        showProgress.postValue(true)
        val idUri = Uri.parse(CONTENT_URI.toString() + "/" + id)
        viewModelScope.launch(Dispatchers.IO) {
            context.contentResolver.delete(idUri,null,null)
        }
        showProgress.postValue(false)
    }

    fun initializeModel(context: Context){
        getFavoriteUsers(context)
    }

}