package com.example.aheena.di.modules

import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.di.scope.ApplicationScope
import com.example.core_api.navigation.router.NavControllerHolder
import com.example.core_api.navigation.router.NavControllerHolderImpl
import com.example.core_api.navigation.router.NavRouter
import com.example.core_impl.navigation.AppRouterImpl
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {

    @MainRouter
    @ApplicationScope
    @Binds
    fun bindMainRouter(router: AppRouterImpl): NavRouter

    @ApplicationScope
    @Binds
    fun bindNavControllerHolder(holder: NavControllerHolderImpl): NavControllerHolder
}
