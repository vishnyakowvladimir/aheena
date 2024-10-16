package com.example.aheena.di.modules

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.example.aheena.navigation.FeatureComposableHolder
import com.example.core.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

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
    fun provideMediatorsHolder(): FeatureComposableHolder {
        return FeatureComposableHolder()
    }
}
