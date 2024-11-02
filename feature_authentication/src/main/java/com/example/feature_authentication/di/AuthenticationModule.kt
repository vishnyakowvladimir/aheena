package com.example.feature_authentication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.di.key.ViewModelKey
import com.example.core.di.scope.FeatureScope
import com.example.core.navigation.router.AbstractNavRouter
import com.example.core.navigation.router.FeatureRouterImp
import com.example.core.navigation.router.NavControllerHolder
import com.example.core.navigation.router.NavControllerHolderImpl
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.view_model_factory.AppViewModelFactory
import com.example.feature_authentication.presentation.biometric.BiometricsViewModel
import com.example.feature_authentication.presentation.container.AuthenticationContainerViewModel
import com.example.feature_authentication.presentation.create_pin.CreatePinViewModel
import com.example.feature_authentication.presentation.login.LoginViewModel
import com.example.feature_authentication.presentation.pin.PinViewModel
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
    fun bindCreatePinCodeViewModel(viewModel: CreatePinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BiometricsViewModel::class)
    fun bindBiometricsViewModel(viewModel: BiometricsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PinViewModel::class)
    fun bindPinCodeViewModel(viewModel: PinViewModel): ViewModel

    companion object {

        @FeatureScope
        @Provides
        fun provideNavControllerHolder(): NavControllerHolder {
            return NavControllerHolderImpl()
        }

        @FeatureScope
        @Provides
        fun provideRouter(
            mainRouter: AbstractNavRouter,
            navControllerHolder: NavControllerHolder,
        ): NavRouter {
            return FeatureRouterImp(
                appRouter = mainRouter,
                navControllerHolder = navControllerHolder,
            )
        }
    }
}
