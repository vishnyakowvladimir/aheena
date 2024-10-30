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
import com.example.feature_authentication.presentation.biometric.BiometricsViewModel
import com.example.feature_authentication.presentation.container.AuthenticationContainerViewModel
import com.example.feature_authentication.presentation.create_pin.CreatePinViewModel
import com.example.feature_authentication.presentation.login.LoginViewModel
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
    @ViewModelKey(AuthenticationContainerViewModel::class)
    fun bindAuthenticationContainerViewModel(viewModel: AuthenticationContainerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatePinViewModel::class)
    fun bindPinCodeViewModel(viewModel: CreatePinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BiometricsViewModel::class)
    fun bindBiometricsViewModel(viewModel: BiometricsViewModel): ViewModel

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
