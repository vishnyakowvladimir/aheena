package com.example.aheena.di.modules

import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.core.di.qualifier.MainRouter
import com.example.core.di.scope.ApplicationScope
import com.example.core.navigation.router.NavControllerHolder
import com.example.core.navigation.router.NavControllerHolderImpl
import com.example.core.navigation.router.NavRouter
import com.example.core_impl.navigation.AppRouterImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface NavigationModule {

    @MainRouter
    @ApplicationScope
    @Binds
    fun bindMainRouter(router: AppRouterImpl): NavRouter

    companion object {

        @ApplicationScope
        @Provides
        fun provideNavControllerHolder(): NavControllerHolder {
            return NavControllerHolderImpl()
        }

        @ApplicationScope
        @Provides
        fun provideComposablesHolder(): FeatureComposablesHolder {
            return FeatureComposablesHolder()
        }
    }
}
