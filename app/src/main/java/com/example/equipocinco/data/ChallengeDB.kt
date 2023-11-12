package com.example.equipocinco.data


import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import com.example.equipocinco.model.Challenge
import com.example.equipocinco.utils.Constants.NAME_BD

@Database(entities = [Challenge::class], version = 1)
abstract class ChallengeDB : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao

    companion object {
        fun getDatabase(context: Context): ChallengeDB{
            return Room.databaseBuilder(
                context.applicationContext,
                ChallengeDB::class.java,
                NAME_BD
            ).build()
        }

    }

}
