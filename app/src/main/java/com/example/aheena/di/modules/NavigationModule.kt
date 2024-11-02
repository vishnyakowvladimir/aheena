package com.example.aheena.di.modules

import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.core.di.qualifier.MainRouter
import com.example.core.di.scope.ApplicationScope
import com.example.core.holder.ActivityHolder
import com.example.core.navigation.router.NavControllerHolder
import com.example.core.navigation.router.NavControllerHolderImpl
import com.example.core.navigation.router.NavRouter
import com.example.core_impl.navigation.AppRouterImpl
import dagger.Module
import dagger.Provides

@Module
interface NavigationModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideNavControllerHolder(): NavControllerHolder {
            return NavControllerHolderImpl()
        }

        @MainRouter
        @ApplicationScope
        @Provides
        fun provideMainRouter(
            navControllerHolder: NavControllerHolder,
            activityHolder: ActivityHolder,
        ): NavRouter {
            return AppRouterImpl(
                navControllerHolder = navControllerHolder,
                activityHolder = activityHolder,
            )
        }

        @ApplicationScope
        @Provides
        fun provideComposablesHolder(): FeatureComposablesHolder {
            return FeatureComposablesHolder()
        }
    }
}
