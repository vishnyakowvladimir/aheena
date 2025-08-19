package com.example.aheena.deeplink

import android.net.Uri
import com.example.core_api.deeplink.AheenaDeeplink
import com.example.core_api.deeplink.DeeplinkLaunchMode
import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.feature_destination.FeaturesDestination
import com.example.core_api.navigation.router.NavRouter
import javax.inject.Inject

class ItunesDeeplink @Inject constructor(
    @MainRouter private val router: NavRouter,
) : AheenaDeeplink {

    override val host: String = "itunes/list"
    override val launchMode: DeeplinkLaunchMode = DeeplinkLaunchMode.WITH_AUTHENTICATION

    override fun follow(uri: Uri): Boolean {
        router.navigate(FeaturesDestination.ItunesDestination)
        return true
    }

    override fun isEnabled(): Boolean = true

    override fun isValid(uri: Uri): Boolean = true
}
