package com.example.aheena.di.modules

import com.example.core_api.deeplink.DeeplinkHandler
import com.example.core_api.di.scope.ApplicationScope
import com.example.core_impl.deeplink.DeeplinkHandlerImpl
import dagger.Binds
import dagger.Module

@Module
interface DeeplinkModule {

    @ApplicationScope
    @Binds
    fun bindDeeplinkHandler(handlerImpl: DeeplinkHandlerImpl): DeeplinkHandler
}
