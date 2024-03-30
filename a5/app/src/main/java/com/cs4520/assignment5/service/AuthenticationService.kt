package com.cs4520.assignment5.service

private const val TEMP_USERNAME = "admin"
private const val TEMP_PASSWORD = "admin"

class AuthenticationService {
    fun login(username: String, password: String): Boolean {
        return username == TEMP_USERNAME && password == TEMP_PASSWORD
    }
}