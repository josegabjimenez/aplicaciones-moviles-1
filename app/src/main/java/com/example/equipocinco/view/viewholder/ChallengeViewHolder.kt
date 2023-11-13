package com.example.equipocinco.view.viewholder

import android.view.MotionEvent
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.equipocinco.databinding.ChallengesListBinding
import com.example.equipocinco.model.Challenge

class ChallengeViewHolder (binding: ChallengesListBinding):RecyclerView.ViewHolder(binding.root) {
    val bindingChallenge = binding

    fun setChallengeList(challenge: Challenge){
        bindingChallenge.tvDescription.text = challenge.description
        setTouchAnimation(bindingChallenge.ivDelete)
        setTouchAnimation(bindingChallenge.ivEdit)
    }

    private fun setTouchAnimation(imageView: ImageView) {
        imageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Escala hacia abajo en el eje X e Y cuando se presiona
                    v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).start()
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Restablece la escala original cuando se libera o se cancela
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                    true
                }
                else -> false
            }
        }
    }
}