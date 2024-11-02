package com.example.feature_main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.di.key.ViewModelKey
import com.example.core.di.qualifier.MainRouter
import com.example.core.di.scope.FeatureScope
import com.example.core.navigation.router.FeatureRouterImp
import com.example.core.navigation.router.NavControllerHolder
import com.example.core.navigation.router.NavControllerHolderImpl
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.view_model_factory.AppViewModelFactory
import com.example.feature_main.container.MainContainerViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal interface MainModule {

    @Binds
    fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainContainerViewModel::class)
    fun bindMainContainerViewModel(viewModel: MainContainerViewModel): ViewModel

    companion object {

        @FeatureScope
        @Provides
        fun provideNavControllerHolder(): NavControllerHolder {
            return NavControllerHolderImpl()
        }

        @FeatureScope
        @Provides
        fun provideRouter(
            @MainRouter mainRouter: NavRouter,
            navControllerHolder: NavControllerHolder,
        ): NavRouter {
            return FeatureRouterImp(
                appRouter = mainRouter,
                navControllerHolder = navControllerHolder,
            )
        }
    }
}
