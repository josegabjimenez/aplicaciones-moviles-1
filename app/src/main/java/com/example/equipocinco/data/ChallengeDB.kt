package com.example.equipocinco.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example
@Database(entities = [Challenge::class], version = 1)
abstract class ChallengeDB : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao

    companion object {

    }

}
