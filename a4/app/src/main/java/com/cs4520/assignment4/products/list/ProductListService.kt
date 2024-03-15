package com.cs4520.assignment4.products.list

import com.cs4520.assignment4.data.DatabaseClient
import com.cs4520.assignment4.products.Product
import com.cs4520.assignment4.products.ProductApiFactory
import com.cs4520.assignment4.products.ProductData
import com.cs4520.assignment4.products.toProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MAX_PAGE_NUMBER = 6

class ProductListService {
    private val apiClient = ProductApiFactory.make()

    fun listProductsFromApi(onResult: (data: List<ProductData>?, success: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = apiClient.listProducts(0)
                withContext(Dispatchers.Main) {
                    if(!res.isSuccessful) {
                        println(res.code())
                        onResult(null, false)
                        return@withContext
                    }

                    val data = res.body()!!
                    onResult(data, true)
                }
            } catch (e: Throwable) {
                println(e.javaClass)
                onResult(null, false)
            }
        }
    }
}