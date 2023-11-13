package com.example.equipocinco.view.fragment

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.addListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.equipocinco.R
import com.example.equipocinco.databinding.FragmentHomeBinding
import com.example.equipocinco.viewmodel.MusicViewModel
import kotlin.random.Random

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var music: MediaPlayer
    private lateinit var interfaceText: TextView
    private lateinit var interfaceTimer: CountDownTimer
    private lateinit var volume_button: ImageView
    private lateinit var star_button: ImageView
    private lateinit var controller_button: ImageView
    private lateinit var plus_button: ImageView
    private lateinit var share_button: ImageView
    private lateinit var toolbar_touch_animation: LottieAnimationView
    private val musicViewModel: MusicViewModel by viewModels()
    private lateinit var bottle: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //se reproduce la musica
        music = MediaPlayer.create(requireContext(),R.raw.home_sound)
        music.isLooping = true
        if(musicViewModel.musicEnabled.value == true){
            music.start()
        }
        //Logica del boton "presioname"
        val lottieButton = binding.pushmeButton
        val layoutParams = lottieButton.layoutParams

        lottieButton.setOnTouchListener { _, motionEvent ->

            println("Start Bottle Animation")
            spinBottle()

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


        interfaceText = binding.interfaceText
        val initial_number = interfaceText.text.toString().toLong()
        startCountdown(initial_number)


        // logica del boton del volumen

        volume_button = binding.toolbarContainer.findViewById(R.id.volume_button)
        volume_button.setOnClickListener{
            volumeHandler(music)
        }
        //logica pequeña animacion touch al oprimir cualquier icono de la toolbar (menos el del volumen que ya se hace arriba)

        star_button = binding.toolbarContainer.findViewById(R.id.star_button)
        controller_button = binding.toolbarContainer.findViewById(R.id.controller_button)
        plus_button = binding.toolbarContainer.findViewById(R.id.plus_button)

        plus_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_challengesListFragment)
        }

        share_button = binding.toolbarContainer.findViewById(R.id.share_button)

        bottle = binding.bottle
        //onToolbarButtonClick(star_button)
        //onToolbarButtonClick(volume_button)
        //onToolbarButtonClick(controller_button)
        //onToolbarButtonClick(plus_button)
        //onToolbarButtonClick(share_button)
    }

    private fun startCountdown(initialNumber: Long, firstTime: Boolean = true){
        interfaceTimer = object : CountDownTimer(initialNumber * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Actualizar el TextView en cada tick
                val secondsRemaining = millisUntilFinished / 1000
                interfaceText.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                // Acciones cuando la cuenta regresiva termina
                interfaceText.visibility = View.GONE

                if (!firstTime){
                    binding.pushmeButton.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "RETOO!", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Iniciar la cuenta regresiva
        interfaceTimer.start()
    }

    override fun onStop() {
        super.onStop()
        if (music.isPlaying){
            music.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if(musicViewModel.musicEnabled.value == true){
            music.start()
        } else {
            volume_button.setImageResource(R.drawable.icono_sin_volumen)
        }
    }
    override fun onDestroy() {
        // Detener la cuenta regresiva al destruir la actividad
        interfaceTimer.cancel()
        super.onDestroy()
    }

    private fun volumeHandler(music: MediaPlayer){

        if(music.isPlaying){
            volume_button.setImageResource(R.drawable.icono_sin_volumen)
            musicViewModel.setMusicEnabled(false)
            music.pause()
        }else{
            volume_button.setImageResource(R.drawable.icono_volumen)
            musicViewModel.setMusicEnabled(true)
            music.start()
        }

    }
    private fun onToolbarButtonClick(view: View){
        view.setOnClickListener {
            view.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(100)
                .withEndAction(Runnable() {
                    run() {
                        view.animate().scaleX(1f).scaleY(1f).setDuration(100)
                    }
                })
                .start();
        }
    }

    private fun spinBottle() {
        // Deshabilitar botón
        binding.pushmeButton.visibility = View.GONE

        // Reproducir sonido de la botella
        val spin_bottle_sound: MediaPlayer = MediaPlayer.create(requireContext(),R.raw.spin_bottle_sound)
        spin_bottle_sound.start()

        // SI la música está activa, se para
        if(music.isPlaying){
            music.pause()
        }

        // Se obtiene la posición actual de la botella
        val currentRotation = bottle.rotation

        // Se genera un ángulo de giro aleatorio
        val randomAngle = currentRotation + 180f + Random.nextFloat() * 720f

        // Se crea el objeto rotation con el angulo de la botella y el ángulo de giro aleatorio
        val rotation = ObjectAnimator.ofFloat(bottle, "rotation", currentRotation, randomAngle)

        // Se agrega una duración de 5 segundos
        rotation.duration = 5000

        // Se crea un interpolador para un movimiento fluido
        rotation.interpolator = LinearInterpolator()

        // Agregar un listener para ver si la animación termino y así ejecutar un método
        // Set up an AnimatorListener to listen for animation end
        rotation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            // Eventos cuando se termina la animación
            override fun onAnimationEnd(animation: Animator) {
                interfaceText.visibility = View.VISIBLE
                // Se ejecuta el contador desde el 3 al 0
                startCountdown(4, firstTime = false)
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        // Se inicia la animación
        rotation.start()
    }



}