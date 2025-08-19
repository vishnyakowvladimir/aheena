package com.example.core_api.deeplink

import android.net.Uri

interface Deeplink {

    val scheme: String
    val host: String
    val launchMode: DeeplinkLaunchMode

    fun follow(uri: Uri): Boolean

    fun isEnabled(uri: Uri): Boolean = true

    fun isValid(uri: Uri): Boolean = true
}
