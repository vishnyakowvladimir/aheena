package com.example.core_api.navigation.feature_destination

import com.example.core_api.navigation.base.BaseDestination
import kotlinx.serialization.Serializable

sealed interface FeaturesDestination : BaseDestination {

    @Serializable
    data object SplashDestination : FeaturesDestination

    @Serializable
    data object AuthenticationDestination : FeaturesDestination

    @Serializable
    data object MainDestination : FeaturesDestination

    @Serializable
    data object TechDestination : FeaturesDestination

    @Serializable
    data object ItunesDestination : FeaturesDestination
}
