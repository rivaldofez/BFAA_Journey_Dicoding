package com.rivaldofez.cubihub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldofez.cubihub.adapter.FavoriteAdapter
import com.rivaldofez.cubihub.databinding.ActivityFavoriteBinding
import com.rivaldofez.cubihub.listener.OnFavoriteClickListener
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.viewmodel.FavoriteUserViewModel

class FavoriteActivity : AppCompatActivity() {
    companion object {
        val KEY_USERNAME = "username"
    }

    private lateinit var binding: ActivityFavoriteBinding
    val layoutManager = LinearLayoutManager(this@FavoriteActivity)
    private lateinit var favoriteUserAdapter : FavoriteAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteUserViewModel = ViewModelProvider(this@FavoriteActivity).get(FavoriteUserViewModel::class.java)

        favoriteUserAdapter = FavoriteAdapter(this@FavoriteActivity)
        binding.rvFavoriteUser.layoutManager = layoutManager
        binding.rvFavoriteUser.adapter = favoriteUserAdapter

        favoriteUserViewModel.initializeModel(application)
        action()

        val test = favoriteUserViewModel.getFavoriteUsers(application)
        Log.d("Testin","getdata"+ test.toString())

//        favoriteUserViewModel.listFavoriteUser.observe(this@FavoriteActivity, {
//            Log.d("Tesmin", "itttt"+ it.toString())
//            favoriteUserAdapter.setFavoriteUsers(it)
//        })
    }

    private fun action() {
        favoriteUserAdapter.setOnClickItemListener(object  : OnFavoriteClickListener {
            override fun onFavoriteDetail(item: View, favoriteUser: DetailUser) {
                val goToDetailActivity = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                goToDetailActivity.putExtra(UsersFragment.KEY_USERNAME, favoriteUser.login)
                startActivity(goToDetailActivity)
            }
        })
    }
}