package com.example.aheena.di.modules

import android.security.keystore.KeyProperties
import com.example.core.crypto.authentication.RefreshTokenCipher
import com.example.core.crypto.rsa.AuthenticationRsaCipher
import com.example.core.crypto.rsa.RsaCipherKeyStore
import com.example.core.crypto.vizhener.IVizhenerCipher
import com.example.core_impl.crypto.authentication.RefreshTokenCipherImpl
import com.example.core_impl.crypto.rsa.AuthenticationRsaCipherImpl
import com.example.core_impl.crypto.rsa.RsaCipherKeyStoreImpl
import com.example.core_impl.crypto.rsa.RsaSecretKeyConfig
import com.example.core_impl.crypto.rsa.model.CipherMode
import com.example.core_impl.crypto.rsa.model.EncryptionPadding
import com.example.core_impl.crypto.rsa.model.RsaCipherConfig
import com.example.core_impl.crypto.vizhener.VizhenerCipher
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.security.spec.MGF1ParameterSpec

private const val LETTERS_DIGITS_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
private const val ALGORITHM_PARAMS_SPEC_MD_NAME = "SHA-256"
private const val ALGORITHM_PARAMS_SPEC_MGF_NAME = "MGF1"

@Module
internal interface CryptoModule {

    @Binds
    fun bindRefreshTokenCipher(cipher: RefreshTokenCipherImpl): RefreshTokenCipher

    @Binds
    fun bindRsaCipherKeyStore(store: RsaCipherKeyStoreImpl): RsaCipherKeyStore

    @Binds
    fun bindAuthenticationRsaCipher(cipher: AuthenticationRsaCipherImpl): AuthenticationRsaCipher

    companion object {

        @Provides
        fun provideVizhenerCipher(): IVizhenerCipher {
            return VizhenerCipher(LETTERS_DIGITS_ALPHABET)
        }

        @Provides
        fun provideRsaCipherConfig(): RsaCipherConfig {
            return RsaCipherConfig(
                cipherMode = CipherMode.ECB,
                encryptionPadding = EncryptionPadding.OAEP_MGF1,
                mdName = ALGORITHM_PARAMS_SPEC_MD_NAME,
                mgfName = ALGORITHM_PARAMS_SPEC_MGF_NAME,
                mgfSpec = MGF1ParameterSpec.SHA1
            )
        }

        @Provides
        fun provideRsaSecretKeyConfig(): RsaSecretKeyConfig {
            return RsaSecretKeyConfig(
                digests = listOf(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512),
                encryptionPadding = EncryptionPadding.RSA_OAEP,
                isUserAuthenticationRequired = true,
                timeout = null,
            )
        }
    }
}
