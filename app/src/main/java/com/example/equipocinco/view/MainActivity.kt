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

    private lateinit var interfaceText: TextView
    private lateinit var interfaceTimer: CountDownTimer
    private lateinit var volume_button: ImageView
    private lateinit var star_button: ImageView
    private lateinit var controller_button: ImageView
    private lateinit var plus_button: ImageView
    private lateinit var share_button: ImageView
    private lateinit var toolbar_touch_animation: LottieAnimationView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //se reproduce la musica
        val music = MediaPlayer.create(this,R.raw.home_sound)
        music.isLooping = true
        music.start()

        //Logica del boton "presioname"
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


        // logica del boton del volumen

        volume_button = findViewById(R.id.volume_button)
        volume_button.setOnClickListener{
            volumeHandler(music)
        }
        //logica pequeña animacion touch al oprimir cualquier icono de la toolbar (menos el del volumen que ya se hace arriba)

        star_button = findViewById(R.id.star_button)
        controller_button = findViewById(R.id.controller_button)
        plus_button = findViewById(R.id.plus_button)
        share_button = findViewById(R.id.share_button)

        toolbar_touch_animation = findViewById(R.id.toolbar_touch_animation)

       // small_touch_toolbar_animation()


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

    private fun volumeHandler(music: MediaPlayer){

        if(music.isPlaying){
            volume_button.setImageResource(R.drawable.icono_sin_volumen)
            music.pause()
            }else{
            volume_button.setImageResource(R.drawable.icono_volumen)
            music.start()
        }

    }


    /*
    private fun small_touch_toolbar_animation(){
        val clickListener = View.OnClickListener {
            // Hacer la animación Lottie visible y comenzarla
            toolbar_touch_animation.visibility = View.VISIBLE
            toolbar_touch_animation.playAnimation()

            // Desactivar el clic para evitar múltiples clics mientras la animación está en curso
            star_button.isClickable = false
            controller_button.isClickable = false
            plus_button.isClickable = false
            share_button.isClickable = false

            // Agregar un listener para restablecer el clic cuando la animación se complete
            toolbar_touch_animation.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    star_button.isClickable = true
                    controller_button.isClickable = true
                    plus_button.isClickable = true
                    share_button.isClickable = true
                    toolbar_touch_animation.visibility = View.INVISIBLE
                    toolbar_touch_animation.cancelAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }

        // Configurar el OnClickListener para cada icono
        star_button.setOnClickListener(clickListener)
        controller_button.setOnClickListener(clickListener)
        plus_button.setOnClickListener(clickListener)
        share_button.setOnClickListener(clickListener)

    }
*/


}