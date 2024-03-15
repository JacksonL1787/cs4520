package com.cs4520.assignment4.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cs4520.assignment4.data.products.ProductDao
import com.cs4520.assignment4.data.products.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}