package com.cs4520.assignment5.api.product

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiClient {
    @GET(ProductApiConfig.LIST_PRODUCTS_ENDPOINT)
    suspend fun listProducts(@Query(ProductApiConfig.PAGE_QUERY_PARAM) pageNumber: Int): Response<List<ProductData>>

    @GET(ProductApiConfig.LIST_RANDOM_PRODUCTS_ENDPOINT)
    suspend fun listRandomProducts(@Query(ProductApiConfig.PAGE_QUERY_PARAM) pageNumber: Int): Response<List<ProductData>>
}


