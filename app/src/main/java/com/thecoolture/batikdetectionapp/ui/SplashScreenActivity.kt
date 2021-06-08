package com.thecoolture.batikdetectionapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import com.thecoolture.batikdetectionapp.MainActivity
import com.thecoolture.batikdetectionapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        var img: ImageView = findViewById(R.id.img_logo)
        var t1: TextView = findViewById(R.id.textView2)
        var t2: TextView = findViewById(R.id.textView3)
        img.animate().scaleX(15f).setDuration(1000).setStartDelay(0)
        img.animate().scaleY(15f).setDuration(1000).setStartDelay(0)
        t1.animate().alpha(1f).setDuration(1000).setStartDelay(1000)
        t1.animate().translationX(-90f).setDuration(1000).setStartDelay(0)
        t2.animate().alpha(1f).setDuration(1000).setStartDelay(1000)
        t2.animate().translationX(-90f).setDuration(1000).setStartDelay(1000)

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)


    }
}