package com.example.feature_authentication.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.example.core.di.key.ViewModelKey
import com.example.core.di.scope.FeatureScope
import com.example.core.utils.view_model_factory.AppViewModelFactory
import com.example.feature_authentication.presentation.login.LoginViewModel
import com.example.feature_authentication.presentation.create_pin.CreatePinViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal interface AuthenticationModule {

    @Binds
    fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatePinViewModel::class)
    fun bindPinCodeViewModel(viewModel: CreatePinViewModel): ViewModel

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
