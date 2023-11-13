package com.example.equipocinco.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.equipocinco.databinding.ChallengesListBinding
import com.example.equipocinco.model.Challenge
import com.example.equipocinco.view.viewholder.ChallengeViewHolder

class ChallengeAdapter (private val listChallenge:MutableList<Challenge>): RecyclerView.Adapter<ChallengeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ChallengesListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = listChallenge[position]
        holder.setChallengeList(challenge)
    }

    override fun getItemCount(): Int {
        return listChallenge.size
    }
}