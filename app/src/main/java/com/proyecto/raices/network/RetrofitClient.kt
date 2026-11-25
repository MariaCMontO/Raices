package com.proyecto.raices.network

object RetrofitClient {


    private const val BASE_URL = "http://10.75.47.124/php_bd_raices/"

    val api:ApiService by lazy{
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}