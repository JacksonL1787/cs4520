package com.cs4520.assignment5.view.ui.product

data class Product(
    val name: String,
    val price: Double,
    val expiryDate: String?,
    val type: ProductType
)
