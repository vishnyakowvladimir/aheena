package com.example.aheena.di.modules

import com.example.core.di.qualifier.MainRouter
import com.example.core.di.scope.ApplicationScope
import com.example.core.navigation.router.NavControllerHolder
import com.example.core.navigation.router.NavControllerHolderImpl
import com.example.core.navigation.router.NavRouter
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
