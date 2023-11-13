package com.example.equipocinco.repository

import android.content.Context
import com.example.equipocinco.data.ChallengeDB
import com.example.equipocinco.data.ChallengeDao
import com.example.equipocinco.model.Challenge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChallengesRepository (val context: Context){
    private var challengeDao:ChallengeDao = ChallengeDB.getDatabase(context).challengeDao()
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
}