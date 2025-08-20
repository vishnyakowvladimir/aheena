package com.example.core_api.deeplink

import android.net.Uri
import com.example.core_api.navigation.base.BaseDestination

interface Deeplink {

    val scheme: String
    val host: String
    val launchMode: DeeplinkLaunchMode

    fun destination(uri: Uri): BaseDestination

    fun isEnabled(): Boolean = true

    fun isValid(uri: Uri): Boolean = true
}
