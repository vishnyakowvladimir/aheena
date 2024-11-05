package com.example.core.crypto.rsa.cipher

import com.example.core.crypto.rsa.cipher.model.CipherHolder
import java.io.IOException
import java.security.GeneralSecurityException

interface RsaCipher {

    @Throws(GeneralSecurityException::class, IOException::class)
    fun encrypt(bytes: ByteArray): ByteArray

    @Throws(GeneralSecurityException::class, IOException::class)
    fun decrypt(bytes: ByteArray, cipher: CipherHolder): ByteArray

    @Throws(GeneralSecurityException::class, IOException::class)
    fun getCipher(): CipherHolder

    fun refuseCryptoKey(): Boolean
}
