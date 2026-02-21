package com.example.feature_motion_layout.navigation

import com.example.core_api.navigation.base.BaseDestination
import kotlinx.serialization.Serializable

internal sealed interface LocalDestinationMotionLayout : BaseDestination {

    @Serializable
    data object MotionLayout : LocalDestinationMotionLayout
}
