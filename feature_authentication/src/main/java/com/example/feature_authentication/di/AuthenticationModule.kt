package com.example.feature_authentication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core_api.di.key.ViewModelKey
import com.example.core_api.di.scope.FeatureScope
import com.example.core_api.navigation.router.FeatureRouterImp
import com.example.core_api.navigation.router.NavControllerHolder
import com.example.core_api.navigation.router.NavControllerHolderImpl
import com.example.core_api.navigation.router.NavRouter
import com.example.core_api.utils.view_model_factory.AppViewModelFactory
import com.example.feature_authentication.presentation.biometric.BiometricsViewModel
import com.example.feature_authentication.presentation.container.AuthenticationContainerViewModel
import com.example.feature_authentication.presentation.create_pin.CreatePinViewModel
import com.example.feature_authentication.presentation.login.LoginViewModel
import com.example.feature_authentication.presentation.pin.PinViewModel
import dagger.Binds
import dagger.Module
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
    fun bindCreatePinCodeViewModel(viewModel: CreatePinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BiometricsViewModel::class)
    fun bindBiometricsViewModel(viewModel: BiometricsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PinViewModel::class)
    fun bindPinCodeViewModel(viewModel: PinViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindNavControllerHolder(holder: NavControllerHolderImpl): NavControllerHolder

    @FeatureScope
    @Binds
    fun bindRouter(holder: FeatureRouterImp): NavRouter
}
