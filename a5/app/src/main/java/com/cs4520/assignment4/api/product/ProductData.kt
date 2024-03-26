package com.cs4520.assignment4.api.product

data class ProductData(
    val name: String,
    val price: Double,
    val expiryDate: String?,
    val type: String
)