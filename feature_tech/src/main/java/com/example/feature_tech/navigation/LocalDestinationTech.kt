package com.example.feature_tech.navigation

import com.example.core.navigation.base.BaseDestination
import kotlinx.serialization.Serializable

internal sealed interface LocalDestinationTech : BaseDestination {

    @Serializable
    data object SelectUrl : LocalDestinationTech
}
