package com.cs4520.assignment1.data

import java.time.LocalDate

sealed class Product(var name: String, var price: Double, var expiryDate: LocalDate?) {
    class Food(name: String, price: Double, expiryDate: LocalDate?): Product(name, price, expiryDate)
    class Equipment(name: String, price: Double, expiryDate: LocalDate?): Product(name, price, expiryDate)
}