package com.example.equipocinco.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.equipocinco.R
import android.content.Intent
import android.animation.ObjectAnimator
import android.widget.ImageView



class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val imageView: ImageView = findViewById(R.id.imageView)
        val slideAnimation = ObjectAnimator.ofFloat(imageView, "translationY", -700f, 0f)
        slideAnimation.duration = 5000
        slideAnimation.start()

        Handler().postDelayed(Runnable () {
        runOnUiThread() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        },5000)
    }
}