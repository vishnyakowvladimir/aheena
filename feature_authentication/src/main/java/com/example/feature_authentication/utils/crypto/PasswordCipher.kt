package com.example.feature_authentication.utils.crypto

import com.example.core.crypto.vizhener.IVizhenerCipher
import javax.inject.Inject

private const val EMPTY_STRING = ""
private const val COLON_CHAR = ':'

class PasswordCipher @Inject constructor(
    private val vizhenerCipher: IVizhenerCipher,
) {

    fun encrypt(refreshToken: String, pinCode: String): String {
        return applyTokenCryptography(refreshToken) { vizhenerCipher.encrypt(it, pinCode) }
    }

    fun decrypt(refreshToken: String, pinCode: String): String {
        return applyTokenCryptography(refreshToken) { vizhenerCipher.decrypt(it, pinCode) }
    }

    private fun applyTokenCryptography(refreshToken: String, cryptographyPredicate: (String) -> String): String {
        return refreshToken.replaceAfter(
            delimiter = COLON_CHAR,
            replacement = cryptographyPredicate(
                refreshToken.substringAfter(
                    delimiter = COLON_CHAR,
                    missingDelimiterValue = EMPTY_STRING,
                )
            ),
            missingDelimiterValue = cryptographyPredicate(refreshToken)
        )
    }
}
