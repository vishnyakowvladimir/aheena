package com.example.feature_authentication.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.example.core.di.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
internal interface AuthenticationModule {

    companion object {

        @FeatureScope
        @Provides
        fun provideNavHostController(context: Context): NavHostController {
            return NavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
                navigatorProvider.addNavigator(DialogNavigator())
            }
        }
    }
}
