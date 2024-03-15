package com.cs4520.assignment4.view.ui.product

data class Product(
    val name: String,
    val price: Double,
    val expiryDate: String?,
    val type: ProductType
)
