package com.example.equipocinco.webservice

import com.example.equipocinco.model.PokemonResponse
import com.example.equipocinco.utils.Constants.POKEMON_ENDPOINT
import retrofit2.http.GET

interface ApiService {
    @GET(POKEMON_ENDPOINT)
    suspend fun getPokemonList(): PokemonResponse

}