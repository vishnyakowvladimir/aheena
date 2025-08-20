package com.example.aheena.deeplink

import android.net.Uri
import com.example.core_api.deeplink.AheenaDeeplink
import com.example.core_api.deeplink.DeeplinkLaunchMode
import com.example.core_api.navigation.base.BaseDestination
import com.example.core_api.navigation.feature_destination.FeaturesDestination
import javax.inject.Inject

class ItunesDeeplink @Inject constructor() : AheenaDeeplink {

    override val host: String = "itunes/list"
    override val launchMode: DeeplinkLaunchMode = DeeplinkLaunchMode.NEED_FOR_AUTHENTICATION

    override fun destination(uri: Uri): BaseDestination {
        return FeaturesDestination.ItunesDestination
    }

    override fun isEnabled(): Boolean = true

    override fun isValid(uri: Uri): Boolean = true
}
