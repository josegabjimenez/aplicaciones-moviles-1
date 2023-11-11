package com.example.equipocinco.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.equipocinco.model.Challenge

interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChallenge(challenge: Challenge)
}