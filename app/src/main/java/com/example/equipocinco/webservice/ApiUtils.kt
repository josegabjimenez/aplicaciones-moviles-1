package com.example.equipocinco.webservice

class ApiUtils {
    companion object {
        fun getApiService() : ApiService {
            return RetrofitClient.getRetrofit().create(ApiService::class.java)
        }
    }
}