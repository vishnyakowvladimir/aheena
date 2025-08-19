package com.example.core_api.deeplink

interface AheenaDeeplink : Deeplink {

    override val scheme: String
        get() = "aheena"
}
