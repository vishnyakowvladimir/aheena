package com.example.feature_main.navigation

import com.example.core_api.navigation.base.BaseDestination
import kotlinx.serialization.Serializable

internal sealed interface LocalDestinationMain : BaseDestination {

    @Serializable
    data object Main : LocalDestinationMain
}
