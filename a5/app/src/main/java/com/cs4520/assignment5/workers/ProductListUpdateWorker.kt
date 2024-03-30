package com.cs4520.assignment5.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.assignment5.api.product.ProductApiFactory
import com.cs4520.assignment5.api.product.ProductData
import com.cs4520.assignment5.data.AppDatabase
import com.cs4520.assignment5.data.products.ProductEntity
import com.cs4520.assignment5.data.products.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductListUpdateWorker(
    appContext: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val productDao = AppDatabase.get(appContext).productDao()
    private val productRepo = ProductRepository(productDao)

    private val productApi = ProductApiFactory().create()

    companion object {
        // Name of page number input data integer. Can be set in a Data.Builder()
        const val PAGE_NUMBER_INPUT = "page_number"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val pageNumber = workerParams.inputData.getInt(PAGE_NUMBER_INPUT, 0)

        try {
            val data = getProductDataListFromApi(pageNumber)

            if (data.isEmpty()) {
                return@withContext Result.success()
            }

            insertIntoDatabase(pageNumber, data)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }


    private suspend fun getProductDataListFromApi(page: Int): List<ProductData> {
        val res = productApi.listRandomProducts(page)

        if (!res.isSuccessful || res.body() == null) {
            throw Exception("Error fetching data")
        }

        return res.body()!!
    }

    private suspend fun insertIntoDatabase(page: Int, productList: List<ProductData>) {
        val productEntities = productList.map { product ->
            ProductEntity(
                name = product.name,
                price = product.price,
                expiryDate = product.expiryDate,
                type = product.type,
                page = page
            )
        }

        productRepo.updateProductsForPage(page, productEntities)
    }
}