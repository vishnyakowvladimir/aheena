package com.example.aheena.di.modules

import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.core.di.scope.ApplicationScope
import com.example.core.navigation.router.AppRouter
import com.example.core_impl.holder.NavControllerHolder
import com.example.core_impl.navigation.AppRouterImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface NavigationModule {

    @ApplicationScope
    @Binds
    fun bindAppRouter(routerImpl: AppRouterImpl): AppRouter

    companion object {
        @ApplicationScope
        @Provides
        fun provideNavHostControllerHolder(): NavControllerHolder {
            return NavControllerHolder()
        }

        @ApplicationScope
        @Provides
        fun provideComposablesHolder(): FeatureComposablesHolder {
            return FeatureComposablesHolder()
        }
    }
}
