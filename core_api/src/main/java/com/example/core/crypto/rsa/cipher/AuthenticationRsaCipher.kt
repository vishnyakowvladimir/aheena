package com.example.core.crypto.rsa.cipher

import com.example.core.crypto.rsa.cipher.model.AuthenticationCryptoObject
import java.io.IOException
import java.security.GeneralSecurityException

interface AuthenticationRsaCipher {

    @Throws(GeneralSecurityException::class, IOException::class)
    fun encrypt(bytes: ByteArray): ByteArray

    @Throws(GeneralSecurityException::class, IOException::class)
    fun decrypt(bytes: ByteArray, authenticatedCryptoObject: AuthenticationCryptoObject): ByteArray

    @Throws(GeneralSecurityException::class, IOException::class)
    fun getAuthenticationCryptoObject(): AuthenticationCryptoObject

    fun refuseCryptoKey(): Boolean
}
