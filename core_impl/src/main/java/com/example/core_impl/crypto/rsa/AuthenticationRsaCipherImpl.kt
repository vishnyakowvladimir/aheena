package com.example.core_impl.crypto.rsa

import android.security.keystore.KeyPermanentlyInvalidatedException
import com.example.core.crypto.rsa.cipher.AuthenticationRsaCipher
import com.example.core.crypto.rsa.store.RsaCipherKeyStore
import com.example.core.crypto.rsa.cipher.model.AuthenticationCryptoObject
import com.example.core_impl.crypto.rsa.model.RsaCipherConfig
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import javax.inject.Inject

class AuthenticationRsaCipherImpl @Inject constructor(
    private val keyStore: RsaCipherKeyStore,
    private val config: RsaCipherConfig,
) : AuthenticationRsaCipher {

    override fun encrypt(bytes: ByteArray): ByteArray {
        val encryptCipher = createCipher() { initEncryptCipher(it) }
        return encryptCipher.doFinal(bytes)
    }

    override fun decrypt(bytes: ByteArray, authenticatedCryptoObject: AuthenticationCryptoObject): ByteArray {
        return authenticatedCryptoObject.cipher.doFinal(bytes)
    }

    override fun getAuthenticationCryptoObject(): AuthenticationCryptoObject {
        val decryptCipher = createCipher() { initDecryptCipher(it) }
        return AuthenticationCryptoObject(decryptCipher)
    }

    override fun refuseCryptoKey(): Boolean {
        return kotlin.runCatching { deleteSecretKey() }.isSuccess
    }

    private fun createCipher(
        initCipherPredicate: (Cipher) -> Unit
    ): Cipher {
        try {
            return Cipher.getInstance(getCipherAlgorithm()).apply(initCipherPredicate)
        } catch (e: Exception) {
            if (e is KeyPermanentlyInvalidatedException) {
                deleteSecretKey()
            }
            throw e
        }
    }

    private fun initEncryptCipher(cipher: Cipher) {
        val publicKey = keyStore.getPublicKey()
        val unrestrictedPublicKey = KeyFactory
            .getInstance(publicKey.algorithm)
            .generatePublic(X509EncodedKeySpec(publicKey.encoded))
        val algorithmParamsSpec = OAEPParameterSpec(
            config.mdName,
            config.mgfName,
            config.mgfSpec,
            PSource.PSpecified.DEFAULT
        )
        cipher.init(Cipher.ENCRYPT_MODE, unrestrictedPublicKey, algorithmParamsSpec)
    }

    private fun initDecryptCipher(cipher: Cipher) {
        cipher.init(Cipher.DECRYPT_MODE, keyStore.getPrivateKey())
    }

    private fun getCipherAlgorithm(): String {
        return "$KEY_ALGORITHM_RSA/${config.cipherMode.id}/${config.encryptionPadding.id}"
    }

    private fun deleteSecretKey() {
        keyStore.deleteSecretKey()
    }
}
