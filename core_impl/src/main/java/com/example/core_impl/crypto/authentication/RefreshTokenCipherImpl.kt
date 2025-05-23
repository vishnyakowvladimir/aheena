package com.example.core_impl.crypto.authentication

import com.example.core_api.crypto.authentication.RefreshTokenCipher
import com.example.core_api.crypto.vizhener.VizhenerCipher
import javax.inject.Inject

private const val EMPTY_STRING = ""
private const val COLON_CHAR = ':'

class RefreshTokenCipherImpl @Inject constructor(
    private val vizhenerCipher: VizhenerCipher,
) : RefreshTokenCipher {

    override fun encrypt(refreshToken: CharSequence, pinCode: CharSequence): CharSequence {
        return applyTokenCryptography(refreshToken) { vizhenerCipher.encrypt(it, pinCode) }
    }

    override fun decrypt(refreshToken: CharSequence, pinCode: CharSequence): CharSequence {
        return applyTokenCryptography(refreshToken) { vizhenerCipher.decrypt(it, pinCode) }
    }

    private fun applyTokenCryptography(
        refreshToken: CharSequence,
        cryptographyPredicate: (CharSequence) -> CharSequence,
    ): CharSequence {
        return refreshToken.toString().replaceAfter(
            delimiter = COLON_CHAR,
            replacement = cryptographyPredicate(
                refreshToken.toString().substringAfter(
                    delimiter = COLON_CHAR,
                    missingDelimiterValue = EMPTY_STRING,
                )
            ).toString(),
            missingDelimiterValue = cryptographyPredicate(refreshToken).toString(),
        )
    }
}
