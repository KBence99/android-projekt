package com.example.androidprojekt.services

object LogInService {

    private val passwordMap = mapOf("admin" to "admin")

    fun logIn(username: String, password: String): Boolean{
        return passwordMap[username].equals(password)
    }
}