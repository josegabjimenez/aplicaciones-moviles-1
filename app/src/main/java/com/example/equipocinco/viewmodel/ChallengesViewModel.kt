package com.example.equipocinco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipocinco.model.Challenge
import com.example.equipocinco.repository.ChallengesRepository
import kotlinx.coroutines.launch


class ChallengesViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val challengesRepository = ChallengesRepository(context)
    private val _challengesList = MutableLiveData<MutableList<Challenge>>()
    val challengesList: LiveData<MutableList<Challenge>> get() = _challengesList

    fun saveChallenge(challenge: Challenge){
        viewModelScope.launch {
            challengesRepository.saveChallenge(challenge)
        }

    }

    fun getChallengesList(){
        viewModelScope.launch {
            _challengesList.value = challengesRepository.getChallengesList()
        }
    }

}