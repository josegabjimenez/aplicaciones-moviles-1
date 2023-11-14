package com.example.equipocinco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipocinco.model.Challenge
import com.example.equipocinco.model.Pokemon
import com.example.equipocinco.repository.ChallengesRepository
import kotlinx.coroutines.launch


class ChallengesViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val challengesRepository = ChallengesRepository(context)
    private val _challengesList = MutableLiveData<MutableList<Challenge>>()
    val challengesList: LiveData<MutableList<Challenge>> get() = _challengesList

    private val _randomChallenge = MutableLiveData<Challenge?>()
    val randomChallenge: LiveData<Challenge?> get() = _randomChallenge

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

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

    fun getRandomChallenge(){
        viewModelScope.launch {
            challengesRepository.getRandomChallenge().collect { challenge ->
                _randomChallenge.value = challenge
            }
        }
    }

    fun getPokemonlist(){
        viewModelScope.launch {
            _pokemonList.value = challengesRepository.getPokemonList()
        }
    }

}