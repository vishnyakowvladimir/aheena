package com.example.core_impl.crypto.rsa

import android.annotation.SuppressLint
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import com.example.core_api.crypto.rsa.store.RsaCipherKeyStore
import com.example.core_impl.crypto.rsa.model.EncryptionPadding
import java.security.Key
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.spec.AlgorithmParameterSpec
import javax.inject.Inject

internal const val KEY_ALGORITHM_RSA = "RSA"
internal const val KEY_STORE_NAME = "AndroidKeyStore"
internal const val KEY_NAME = "KeyName"

class RsaCipherKeyStoreImpl @Inject constructor(
    private val secretKeyConfig: RsaSecretKeyConfig,
) : RsaCipherKeyStore {

    private val keyStore: KeyStore
        get() {
            return KeyStore.getInstance(KEY_STORE_NAME).apply { load(null) }
        }

    override fun deleteSecretKey() {
        keyStore.deleteEntry(KEY_NAME)
    }

    override fun getPrivateKey(): Key {
        return generateKeyPair().run { keyStore.getKey(KEY_NAME, null) }
    }

    override fun getPublicKey(): Key {
        return generateKeyPair().run { keyStore.getCertificate(KEY_NAME).publicKey }
    }

    private fun hasKey(): Boolean {
        return keyStore.containsAlias(KEY_NAME)
    }

    private fun generateKeyPair() {
        if (!hasKey()) {
            with(KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA, KEY_STORE_NAME)) {
                initialize(generateKeyAlgorithmSpec())
                generateKeyPair()
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun generateKeyAlgorithmSpec(): AlgorithmParameterSpec {
        return KeyGenParameterSpec
            .Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setDigests(*secretKeyConfig.digests.toTypedArray())
            .setEncryptionPaddings(secretKeyConfig.encryptionPadding.id)
            .apply {
                setUserAuthenticationRequired(secretKeyConfig.isUserAuthenticationRequired)
                if (secretKeyConfig.timeout != null) {
                    setValidityTimeout(secretKeyConfig.timeout)
                }
            }
            .build()
    }

    private fun KeyGenParameterSpec.Builder.setValidityTimeout(timeout: RsaSecretKeyTimeout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setUserAuthenticationParameters(
                timeout.seconds,
                timeout.authType.map { it.value }.fold(0) { current, next -> current or next }
            )
        } else {
            @Suppress("DEPRECATION")
            setUserAuthenticationValidityDurationSeconds(timeout.seconds)
        }
    }
}

/**
 * Конфиг ключа
 * @property digests Алгоритмы, например SHA-256 SHA-512
 * @property encryptionPadding Падинг щифрования
 * @property isUserAuthenticationRequired Признак необходимой обязательной аутентификации в устройстве
 * @property timeout Время доступа к ключу после очередной аутентификации в устройстве. Если null, тогда
 * нужно каждый раз проходить аутентификацию на устройстве
 */
data class RsaSecretKeyConfig(
    val digests: List<String>,
    val encryptionPadding: EncryptionPadding,
    val isUserAuthenticationRequired: Boolean,
    val timeout: RsaSecretKeyTimeout?,
)

/**
 * Время доступа к ключу после очередной аутентификации пользователя в устройстве
 * @property seconds Время доступа в секундах
 * @property authType Тип аутентификации пользователя, после которой открывается доступ к ключу
 */
data class RsaSecretKeyTimeout(
    val seconds: Int,
    val authType: Set<RsaSecretKeyAuthType>,
)

/**
 * Типы аутентификации в устройстве для доступа к ключу в пределах интервала времени
 * Для биометрии - тип BIOMETRIC_STRONG, в остальных случаях DEVICE_CREDENTIAL
 */
@RequiresApi(Build.VERSION_CODES.R)
enum class RsaSecretKeyAuthType(val value: Int) {
    BIOMETRIC_STRONG(KeyProperties.AUTH_BIOMETRIC_STRONG),
    DEVICE_CREDENTIAL(KeyProperties.AUTH_DEVICE_CREDENTIAL),
}
