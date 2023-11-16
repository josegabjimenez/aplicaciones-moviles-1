package com.example.equipocinco.repository

import android.content.Context
import com.example.equipocinco.data.ChallengeDB
import com.example.equipocinco.data.ChallengeDao
import com.example.equipocinco.model.Challenge
import com.example.equipocinco.model.Pokemon
import com.example.equipocinco.webservice.ApiService
import com.example.equipocinco.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ChallengesRepository (val context: Context){
    private var challengeDao:ChallengeDao = ChallengeDB.getDatabase(context).challengeDao()
    private var apiService: ApiService = ApiUtils.getApiService()
    suspend fun saveChallenge(challenge: Challenge){
        withContext(Dispatchers.IO){
            challengeDao.saveChallenge(challenge)
        }
    }
    suspend fun getChallengesList():MutableList<Challenge>{
        return withContext(Dispatchers.IO){
            challengeDao.getChallengesList()
        }
    }

    suspend fun getRandomChallenge(): Flow<Challenge?> {
        return withContext(Dispatchers.IO){
            challengeDao.getRandomChallenge()
        }
    }

    suspend fun getPokemonList(): List<Pokemon> {
        return withContext(Dispatchers.IO){
            try {
                val response = apiService.getPokemonList()
                response.pokemon
            } catch (e: Exception){
                // Manejar el error
                e.printStackTrace()
                mutableListOf()
            }
        }
    }
    suspend fun getChallengeById(challengeId: Int): Challenge? {
        return withContext(Dispatchers.IO) {
            challengeDao.getChallengeById(challengeId)
        }
    }

    suspend fun updateChallenge(challenge: Challenge) {
        withContext(Dispatchers.IO) {
            challengeDao.updateChallenge(challenge)
        }
    }

    suspend fun deleteChallenge(challengeId: Int) {
        withContext(Dispatchers.IO) {
            challengeDao.deleteChallenge(challengeId)
        }
    }


}