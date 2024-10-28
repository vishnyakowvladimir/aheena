package com.example.core.crypto.vizhener

interface IVizhenerCipher {
    fun encrypt(text: String, key: String): String
    fun decrypt(text: String, key: String): String
}
