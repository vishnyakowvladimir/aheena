package com.example.core_impl.utils.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.core_api.utils.shared_preferences.EncryptedSharedPreferencesProvider
import java.security.KeyStore
import javax.inject.Inject

private const val CRYPTO_PREFERENCES = "CRYPTO_PREFERENCES"

class EncryptedSharedPreferencesProviderImpl @Inject constructor(private val context: Context) :
    EncryptedSharedPreferencesProvider {

    companion object {
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    }

    private var masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    override fun getEncryptedSharedPreferences(isRecovery: Boolean): SharedPreferences {
        try {
            return EncryptedSharedPreferences.create(
                context,
                CRYPTO_PREFERENCES,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            if (isRecovery) throw e

            deleteMasterKeyFromKeyStore()
            masterKey = recreateMasterKey(context)
            context.deleteSharedPreferences(CRYPTO_PREFERENCES)
            return getEncryptedSharedPreferences(true)
        }
    }

    private fun deleteMasterKeyFromKeyStore() {
        val keystore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keystore.load(null)
        keystore.deleteEntry(MasterKey.DEFAULT_MASTER_KEY_ALIAS)
    }

    private fun recreateMasterKey(context: Context): MasterKey {
        return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }
}
