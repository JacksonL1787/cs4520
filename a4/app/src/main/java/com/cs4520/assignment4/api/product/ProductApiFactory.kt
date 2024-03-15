package com.cs4520.assignment4.api.product

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductApiFactory {
    fun create(): ProductApiClient {
        return Retrofit.Builder()
            .baseUrl(ProductApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiClient::class.java)
    }
}