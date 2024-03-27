package com.cs4520.assignment5.api.product

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiClient {
    @GET(ProductApiConfig.LIST_PRODUCTS_ENDPOINT)
    suspend fun listProducts(@Query("page") pageNumber: Int): Response<List<ProductData>>
}


