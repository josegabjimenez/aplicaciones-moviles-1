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


class MainActivity : AppCompatActivity() {

    private lateinit var interfaceText: TextView
    private lateinit var interfaceTimer: CountDownTimer
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //se reproduce la musica
        val music = MediaPlayer.create(this,R.raw.home_sound)
        music.isLooping = true
        music.start()

        //Logica del boton interactivo
        val lottieButton = findViewById<LottieAnimationView>(R.id.pushmeButton)
        val layoutParams = lottieButton.layoutParams

        lottieButton.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    layoutParams.width = layoutParams.width - 40
                    layoutParams.height = layoutParams.width - 40
                    lottieButton.layoutParams = layoutParams
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                    layoutParams.width = layoutParams.width + 40
                    layoutParams.height = layoutParams.width + 40
                    lottieButton.layoutParams = layoutParams
                    true
                }
                else -> false
            }
        }

        //logica de la cuenta regresiva


        interfaceText = findViewById(R.id.interfaceText)
        val initial_number = interfaceText.text.toString().toLong()
        startCountdown(initial_number)

    }


    private fun startCountdown(initialNumber: Long){
         interfaceTimer = object : CountDownTimer(initialNumber * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Actualizar el TextView en cada tick
                val secondsRemaining = millisUntilFinished / 1000
                interfaceText.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                // Acciones cuando la cuenta regresiva termina
                interfaceText.visibility = View.GONE
            }
        }

        // Iniciar la cuenta regresiva
        interfaceTimer.start()
    }
    override fun onDestroy() {
        // Detener la cuenta regresiva al destruir la actividad
        interfaceTimer.cancel()
        super.onDestroy()
    }



}