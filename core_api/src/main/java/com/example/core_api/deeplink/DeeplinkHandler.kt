package com.example.core_api.deeplink

import android.net.Uri

interface DeeplinkHandler {

    fun handle(uri: Uri)
}
