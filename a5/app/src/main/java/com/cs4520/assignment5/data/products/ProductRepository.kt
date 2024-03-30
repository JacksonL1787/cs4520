package com.cs4520.assignment5.data.products

class ProductRepository(private val productDao: ProductDao) {
    suspend fun getProductsFromPage(page: Int): List<ProductEntity> {
        return productDao.getProductsFromPage(page)
    }

    suspend fun getPageNumbers(): List<Int> {
        // Returns first page if none are present in database
        return productDao.getPageNumbers().ifEmpty { listOf(0) }
    }

    suspend fun updateProductsForPage(page: Int, productEntities: List<ProductEntity>) {
        productDao.deleteProductsByPage(page)
        productDao.insertAll(productEntities)
    }
}