package com.example.core.crypto.vizhener

interface IVizhenerCipher {
    fun encrypt(text: CharSequence, key: CharSequence): CharSequence
    fun decrypt(text: CharSequence, key: CharSequence): CharSequence
}
