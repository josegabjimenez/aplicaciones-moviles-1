package com.example.equipocinco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipocinco.model.Challenge
import com.example.equipocinco.model.Pokemon
import com.example.equipocinco.repository.ChallengesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChallengesViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    private val challengesRepository = ChallengesRepository(context)
    private val _challengesList = MutableLiveData<MutableList<Challenge>>()
    val challengesList: LiveData<MutableList<Challenge>> get() = _challengesList

    private val _randomChallenge = MutableLiveData<Challenge?>()
    val randomChallenge: LiveData<Challenge?> get() = _randomChallenge

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    private val _editChallengeId = MutableLiveData<Int?>()
    val editChallengeId: LiveData<Int?> get() = _editChallengeId

    fun saveChallenge(challenge: Challenge) {
        viewModelScope.launch {
            challengesRepository.saveChallenge(challenge)
        }
    }

    fun getChallengesList() {
        viewModelScope.launch {
            _challengesList.value = challengesRepository.getChallengesList()
        }
    }

    fun getRandomChallenge() {
        viewModelScope.launch {
            challengesRepository.getRandomChallenge().collect { challenge ->
                _randomChallenge.value = challenge
            }
        }
    }

    fun getPokemonlist() {
        viewModelScope.launch {
            _pokemonList.value = challengesRepository.getPokemonList()
        }
    }

    fun getChallengeById(challengeId: Int): Challenge? {
        return runBlocking {
            challengesRepository.getChallengeById(challengeId)
        }
    }

    // Método para actualizar la descripción de un Challenge

    fun updateChallengeDescription(challengeId: Int, newDescription: String) {
        viewModelScope.launch {
            // Obtener el desafío por ID
            val challengeToUpdate = challengesRepository.getChallengeById(challengeId)

            // Actualizar la descripción si se encontró el desafío
            if (challengeToUpdate != null) {
                challengeToUpdate.description = newDescription
                challengesRepository.updateChallenge(challengeToUpdate)

                // Actualizar la lista de desafíos después de la actualización
                _challengesList.value = challengesRepository.getChallengesList()
            }
        }
    }

    fun deleteChallenge(challengeId: Int) {
        viewModelScope.launch {
            challengesRepository.deleteChallenge(challengeId)

            // Después de eliminar el desafío, actualiza la lista
            getChallengesList()
        }
    }

}
