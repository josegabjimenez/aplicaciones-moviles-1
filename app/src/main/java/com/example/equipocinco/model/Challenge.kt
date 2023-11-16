package com.example.equipocinco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Challenge(
    @PrimaryKey (autoGenerate = true)
    val id:Int = 0,
    var description:String
)
