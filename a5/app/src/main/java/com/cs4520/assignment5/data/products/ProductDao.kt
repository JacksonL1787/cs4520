package com.cs4520.assignment5.data.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE page = :page")
    suspend fun getProductsFromPage(page: Int): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productEntities: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun nukeTable()

    @Query("DELETE FROM products WHERE page = :page")
    suspend fun deleteProductsByPage(page: Int)
}