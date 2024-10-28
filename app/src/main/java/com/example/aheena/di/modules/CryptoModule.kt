package com.example.aheena.di.modules

import com.example.core.crypto.vizhener.IVizhenerCipher
import com.example.core_impl.crypto.VizhenerCipher
import dagger.Module
import dagger.Provides

private const val LETTERS_DIGITS_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

@Module
internal interface CryptoModule {

    companion object {

        @Provides
        fun provideVizhenerCipher(): IVizhenerCipher {
            return VizhenerCipher(LETTERS_DIGITS_ALPHABET)
        }
    }
}
