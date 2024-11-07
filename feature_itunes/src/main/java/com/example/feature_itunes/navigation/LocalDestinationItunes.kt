package com.example.feature_itunes.navigation

import com.example.core.navigation.base.BaseDestination
import kotlinx.serialization.Serializable

internal sealed interface LocalDestinationItunes : BaseDestination {

    @Serializable
    data object Itunes : LocalDestinationItunes
}
