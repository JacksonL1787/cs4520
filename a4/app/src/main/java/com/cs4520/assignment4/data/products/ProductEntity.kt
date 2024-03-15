package com.cs4520.assignment4.data.products

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(
    tableName = "products",
)
data class ProductEntity(
    @PrimaryKey
    val name: String,
    val price: Double,
    val expiryDate: String?,
    val type: String,
    val page: Int,
)