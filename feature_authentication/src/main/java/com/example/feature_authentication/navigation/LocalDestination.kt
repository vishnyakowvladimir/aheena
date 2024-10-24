package com.example.feature_authentication.navigation

import kotlinx.serialization.Serializable

internal sealed interface LocalDestination {
    @Serializable
    data object PhoneAndPassword : LocalDestination

    @Serializable
    data object Pin : LocalDestination
}
