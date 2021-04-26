package com.rivaldofez.cubihub

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rivaldofez.cubihub.adapter.DetailPagerAdapter
import com.rivaldofez.cubihub.database.DetailUserDatabase.Companion.CONTENT_URI
import com.rivaldofez.cubihub.databinding.ActivityUserDetailBinding
import com.rivaldofez.cubihub.helper.toContentValues
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.provider.UserProvider
import com.rivaldofez.cubihub.viewmodel.DetailUserViewModel
import com.rivaldofez.cubihub.viewmodel.FavoriteUserViewModel

class UserDetailActivity : AppCompatActivity() {
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var username: String
    private lateinit var binding:ActivityUserDetailBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        detailUserViewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        favoriteUserViewModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)

        detailUserViewModel.loadDetailUser(username)
        detailUserViewModel.detailUser.observe(this,{
            setUserView(it)

            favoriteUserViewModel.insertUsers(applicationContext,it)
            favoriteUserViewModel.getFavoriteUsers(applicationContext)

        })

        detailUserViewModel.showProgress.observe(this,{
            if (it)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        })

        val detailPagerAdapter = DetailPagerAdapter(this)
        detailPagerAdapter.username = username

        binding.viewPager.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager, ) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun initView() {
        username = intent.getStringExtra(UsersFragment.KEY_USERNAME)!!
    }

    private fun setUserView(detailUser : DetailUser){
        if(detailUser.login != "null") binding.tvUsername.text = detailUser.login
        else binding.tvUsername.text = getString(R.string.nulldata)

        if(detailUser.name != "null") binding.tvFullname.text = detailUser.name
        else binding.tvFullname.text = getString(R.string.nulldata)

        if(detailUser.location != "null") binding.tvLocation.text = detailUser.location
        else binding.tvLocation.text = getString(R.string.nulldata)

        if(detailUser.public_repos != null) binding.tvRepository.text = detailUser.public_repos.toString()
        else  binding.tvRepository.text = getString(R.string.nulldata)

        if( detailUser.followers != null) binding.tvFollower.text = detailUser.followers.toString()
        else  binding.tvFollower.text = getString(R.string.nulldata)

        if(  detailUser.following != null)  binding.tvFollowing.text = detailUser.following.toString()
        else   binding.tvFollowing.text = getString(R.string.nulldata)

        Glide.with(this).load(detailUser.avatar_url).into(binding.imgContent)
    }
}