package com.rivaldofez.cubihub.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rivaldofez.consumerapp.FollowFragment

class DetailPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    companion object{
        val endFollowers = "followers"
        val endFollowing = "following"
    }
    var username:String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> {
                val fragmentFollower = FollowFragment()
                fragmentFollower.username = username
                fragmentFollower.option = endFollowers
                fragment = fragmentFollower
            }
            1 -> {
                val fragmentFollowing = FollowFragment()
                fragmentFollowing.username = username
                fragmentFollowing.option = endFollowing
                fragment = fragmentFollowing
            }
        }
        return fragment!!
    }
}