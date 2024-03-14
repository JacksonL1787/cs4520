package com.cs4520.assignment4.products.list

import com.cs4520.assignment4.products.Product
import com.cs4520.assignment4.products.ProductApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class ProductListService {
    private val apiClient = ProductApiFactory.make()

    private val pageNumber = 0

    fun listProducts(onResult: (List<Product>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val res = apiClient.listProducts(0)
            withContext(Dispatchers.Main) {
                try {
                    if (res.isSuccessful) {
                        println(res.body())
                        onResult(res.body())
                    }
                } catch (e: HttpException) {
                    println("Exception ${e.message}")
                } catch (e: Throwable) {
                    println("Ooops: Something else went wrong")
                }

            }
        }
    }
}