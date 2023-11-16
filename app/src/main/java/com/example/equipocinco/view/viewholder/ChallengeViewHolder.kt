package com.example.equipocinco.view.viewholder

import android.view.MotionEvent
import com.example.equipocinco.view.viewholder.OnEditClickListener

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.equipocinco.databinding.ChallengesListBinding
import com.example.equipocinco.model.Challenge
interface OnEditClickListener {
    fun onEditClick(challengeId: Int)
    fun onDeleteClick(challengeId: Int)
}

class ChallengeViewHolder (binding: ChallengesListBinding,private val editClickListener: OnEditClickListener):RecyclerView.ViewHolder(binding.root) {
    val bindingChallenge = binding

    fun setChallengeList(challenge: Challenge){
        bindingChallenge.tvDescription.text = challenge.description


        bindingChallenge.ivEdit.setOnClickListener {
            // Llamar al método onEditClick del listener
            editClickListener.onEditClick(challenge.id)
        }
        bindingChallenge.ivDelete.setOnClickListener {
            // Llamar al método onDeleteClick del listener
            editClickListener.onDeleteClick(challenge.id)
        }

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