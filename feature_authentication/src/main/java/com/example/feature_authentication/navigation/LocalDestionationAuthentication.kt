package com.example.feature_authentication.navigation

import kotlinx.serialization.Serializable

internal sealed interface LocalDestionationAuthentication {
    @Serializable
    data object PhoneAndPassword : LocalDestionationAuthentication

    @Serializable
    data object CreatePin : LocalDestionationAuthentication

    @Serializable
    data object Biometrics : LocalDestionationAuthentication
}
