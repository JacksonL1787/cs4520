package com.cs4520.assignment4.products

import java.time.LocalDate

sealed class Product(
    var name: String,
    var price: Double,
    var expiryDate: LocalDate?,
    var type: String
) {
    class Food(name: String, price: Double, expiryDate: LocalDate?) :
        Product(name, price, expiryDate, "Food")

    class Equipment(name: String, price: Double, expiryDate: LocalDate?) :
        Product(name, price, expiryDate, "Entertainment")

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Product

        return name == other.name
                && price == other.price
                && expiryDate == other.expiryDate
                && type == other.type
    }

    override fun hashCode(): Int {
        return name.hashCode() + price.hashCode() + expiryDate.hashCode() + type.hashCode()
    }
}