package com.example.core_api.crypto.rsa.store

import java.io.IOException
import java.security.GeneralSecurityException
import java.security.Key

interface RsaCipherKeyStore {

    @Throws(GeneralSecurityException::class, IOException::class)
    fun deleteSecretKey()

    @Throws(GeneralSecurityException::class, IOException::class)
    fun getPrivateKey(): Key

    @Throws(GeneralSecurityException::class, IOException::class)
    fun getPublicKey(): Key
}
