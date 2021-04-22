package com.rivaldofez.cubihub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rivaldofez.cubihub.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.fl_fragment, UsersFragment())
        fragment.commit()

        binding.bnavMain.setOnNavigationItemSelectedListener (onBottomNavListener)
    }

    private val onBottomNavListener = BottomNavigationView.OnNavigationItemSelectedListener { i ->
        var selectedFragment: Fragment = UsersFragment()

        when(i.itemId){
            R.id.item_users -> {
                selectedFragment = UsersFragment()
                binding.bnavMain.getOrCreateBadge(R.id.item_users).apply {
                    isVisible = false
                    number = 0
                }
            }
            R.id.item_about -> {
                selectedFragment = AboutFragment()
            }
        }

        val fr = supportFragmentManager.beginTransaction()
        fr.replace(R.id.fl_fragment, selectedFragment)
        fr.commit()
        true
    }
}