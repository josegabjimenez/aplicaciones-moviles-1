package com.example.equipocinco.view


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.equipocinco.R
import android.view.MotionEvent
import android.widget.TextView
import android.os.CountDownTimer
import android.view.View
import android.media.MediaPlayer
import android.widget.ImageView
import android.animation.Animator

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}