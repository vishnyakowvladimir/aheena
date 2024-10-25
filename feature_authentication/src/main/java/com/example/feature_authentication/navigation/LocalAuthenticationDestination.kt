package com.example.feature_authentication.navigation

import kotlinx.serialization.Serializable

internal sealed interface LocalAuthenticationDestination {
    @Serializable
    data object PhoneAndPassword : LocalAuthenticationDestination

    @Serializable
    data object CreatePin : LocalAuthenticationDestination
}
