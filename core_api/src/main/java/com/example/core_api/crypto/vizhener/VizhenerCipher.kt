package com.example.core_api.crypto.vizhener

interface VizhenerCipher {
    fun encrypt(text: CharSequence, key: CharSequence): CharSequence
    fun decrypt(text: CharSequence, key: CharSequence): CharSequence
}
