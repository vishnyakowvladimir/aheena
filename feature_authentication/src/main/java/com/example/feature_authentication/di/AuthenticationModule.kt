package com.example.feature_authentication.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.example.core.di.key.ViewModelKey
import com.example.core.di.scope.FeatureScope
import com.example.feature_authentication.presentation.phone_and_password.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal interface AuthenticationModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

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
