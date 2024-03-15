package com.cs4520.assignment4.products

import java.time.LocalDate

data class ProductData(
    val name: String,
    val price: Double,
    val expiryDate: String?,
    val type: String
)

data class Product(
    val name: String,
    val price: Double,
    val expiryDate: LocalDate?,
    val type: ProductType
)

fun ProductData.toProduct(): Product {
    val expiryDate: LocalDate? = this.expiryDate?.let { LocalDate.parse(it) }
    val type = getProductType(this.type)

    return Product(
        name = this.name,
        price = this.price,
        expiryDate = expiryDate,
        type = type,
    )
}

private fun getProductType(type: String): ProductType {
    return when (type) {
        "Food" -> ProductType.FOOD
        "Equipment" -> ProductType.EQUIPMENT
        else -> throw IllegalArgumentException("product type not recognized")
    }
}