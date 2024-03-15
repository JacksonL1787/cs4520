package com.cs4520.assignment4.data.products

class ProductRepository(private val productDao: ProductDao) {
    suspend fun getProductsFromPage(page: Int): List<ProductEntity> {
        return productDao.getProductsFromPage(page)
    }

    suspend fun updateProductsForPage(page: Int, productEntities: List<ProductEntity>) {
        productDao.deleteProductsByPage(page)
        productDao.insertAll(productEntities)
    }
}