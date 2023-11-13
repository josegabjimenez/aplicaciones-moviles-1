package com.example.equipocinco.view.fragment

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.IpSecManager.ResourceUnavailableException
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.equipocinco.R
import com.example.equipocinco.databinding.FragmentHomeBinding

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
        music.start()

        //Logica del boton "presioname"
        val lottieButton = binding.pushmeButton
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


        //onToolbarButtonClick(star_button)
        //onToolbarButtonClick(volume_button)
        //onToolbarButtonClick(controller_button)
        //onToolbarButtonClick(plus_button)
        //onToolbarButtonClick(share_button)
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

    override fun onStop() {
        super.onStop()
        if (music.isPlaying){
            music.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if(!music.isPlaying){
            music.start()
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
            music.pause()
        }else{
            volume_button.setImageResource(R.drawable.icono_volumen)
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





}