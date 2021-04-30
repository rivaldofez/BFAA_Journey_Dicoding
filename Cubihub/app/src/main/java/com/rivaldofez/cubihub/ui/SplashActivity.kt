package com.rivaldofez.cubihub.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.rivaldofez.cubihub.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME: Long = 3000
    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME)
    }
}