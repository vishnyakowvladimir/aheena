package com.example.aheena.di.modules

import com.example.core_api.deeplink.Deeplink
import com.example.core_api.deeplink.DeeplinkHandler
import com.example.core_api.di.scope.ApplicationScope
import com.example.core_impl.deeplink.DeeplinkHandlerImpl
import com.example.deeplink.deeplinks.ItunesDeeplink
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface DeeplinkModule {

    @ApplicationScope
    @Binds
    fun bindDeeplinkHandler(handler: DeeplinkHandlerImpl): DeeplinkHandler

    @Binds
    @IntoSet
    fun bindDeeplink(deeplink: ItunesDeeplink): Deeplink
}
