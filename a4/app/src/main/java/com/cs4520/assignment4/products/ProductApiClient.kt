package com.cs4520.assignment4.products

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"
private const val LIST_PRODUCTS_ENDPOINT: String = "prod/"

interface ProductApiClient {
    @GET(LIST_PRODUCTS_ENDPOINT)
    suspend fun listProducts(@Query("page") pageNumber: Int): Response<List<ProductData>>
}

object ProductApiFactory {
    fun make(): ProductApiClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiClient::class.java)
    }
}
