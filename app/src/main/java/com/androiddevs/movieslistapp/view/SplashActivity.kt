package com.androiddevs.movieslistapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.androiddevs.movieslistapp.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for 2 seconds (adjust as needed)
        Handler().postDelayed({
            // Launch the main activity
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            // Finish the splash activity
            finish()
        }, 2000)
    }
}
