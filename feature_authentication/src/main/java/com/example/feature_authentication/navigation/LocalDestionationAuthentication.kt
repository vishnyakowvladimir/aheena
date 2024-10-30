package com.example.feature_authentication.navigation

import com.example.core.navigation.base.BaseDestination
import kotlinx.serialization.Serializable

internal sealed interface LocalDestionationAuthentication : BaseDestination {
    @Serializable
    data object PhoneAndPassword : LocalDestionationAuthentication

    @Serializable
    data object CreatePin : LocalDestionationAuthentication

    @Serializable
    data object Biometrics : LocalDestionationAuthentication
}
