package com.example.aheena.di.modules

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.core.di.scope.ApplicationScope
import com.example.core.navigation.router.AppRouter
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
        fun provideNavHostController(context: Context): NavHostController {
            return NavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
                navigatorProvider.addNavigator(DialogNavigator())
            }
        }

        @ApplicationScope
        @Provides
        fun provideComposablesHolder(): FeatureComposablesHolder {
            return FeatureComposablesHolder()
        }
    }
}
