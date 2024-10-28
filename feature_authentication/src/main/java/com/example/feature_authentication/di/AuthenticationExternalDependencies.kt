package com.example.feature_authentication.di

import android.content.Context
import com.example.core.crypto.vizhener.IVizhenerCipher
import com.example.core.navigation.router.AppRouter
import com.example.core.utils.string_provider.StringProvider
import javax.inject.Inject

class AuthenticationExternalDependencies @Inject constructor(
    val context: Context,
    val router: AppRouter,
    val stringProvider: StringProvider,
    val vizhenerCipher: IVizhenerCipher,
)
