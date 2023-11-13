package com.example.equipocinco.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel(){
    private val _musicEnabled = MutableLiveData<Boolean>()
    val musicEnabled: LiveData<Boolean> get() = _musicEnabled

    fun setMusicEnabled(enabled:Boolean){
        _musicEnabled.value = enabled
    }
}