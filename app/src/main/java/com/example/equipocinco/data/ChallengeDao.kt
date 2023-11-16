package com.example.equipocinco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.equipocinco.model.Challenge
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChallenge(challenge: Challenge)

    @Query("SELECT * FROM Challenge")
    suspend fun getChallengesList(): MutableList<Challenge>

    @Query("SELECT * FROM Challenge ORDER BY RANDOM() LIMIT 1")
    fun getRandomChallenge(): Flow<Challenge?>


    @Update
    suspend fun updateChallenge(challenge: Challenge)

    @Query("UPDATE Challenge SET description = :newDescription WHERE id = :challengeId")
    suspend fun updateChallengeDescription(challengeId: Int, newDescription: String)

    @Query("SELECT * FROM challenge WHERE id = :challengeId")
    fun getChallengeById(challengeId: Int): Challenge?

    @Query("DELETE FROM challenge WHERE id = :challengeId")
    suspend fun deleteChallenge(challengeId: Int)
}