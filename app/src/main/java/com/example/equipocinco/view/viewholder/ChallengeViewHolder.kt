package com.example.equipocinco.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.equipocinco.databinding.ChallengesListBinding
import com.example.equipocinco.model.Challenge

class ChallengeViewHolder (binding: ChallengesListBinding):RecyclerView.ViewHolder(binding.root) {
    val bindingChallenge = binding

    fun setChallengeList(challenge: Challenge){
        bindingChallenge.tvDescription.text = challenge.description
    }
}