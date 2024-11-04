package com.example.feature_authentication.navigation

import com.example.core.navigation.base.BaseDestination
import kotlinx.serialization.Serializable

internal sealed interface LocalDestinationAuthentication : BaseDestination {

    @Serializable
    data object PhoneAndPassword : LocalDestinationAuthentication

    @Serializable
    data object CreatePin : LocalDestinationAuthentication

    @Serializable
    data object Biometrics : LocalDestinationAuthentication

    @Serializable
    data object Pin : LocalDestinationAuthentication
}
