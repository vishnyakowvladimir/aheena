package com.example.core.crypto.rsa.model

import javax.crypto.Cipher

data class AuthenticationCryptoObject(
    val cipher: Cipher,
)
