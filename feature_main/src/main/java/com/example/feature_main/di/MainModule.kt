package com.example.feature_main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.di.key.ViewModelKey
import com.example.core.di.scope.FeatureScope
import com.example.core.navigation.router.FeatureRouterImp
import com.example.core.navigation.router.NavControllerHolder
import com.example.core.navigation.router.NavControllerHolderImpl
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.view_model_factory.AppViewModelFactory
import com.example.feature_main.presentation.container.MainContainerViewModel
import com.example.feature_main.presentation.features.FeaturesViewModel
import com.example.feature_main.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface MainModule {

    @Binds
    fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainContainerViewModel::class)
    fun bindMainContainerViewModel(viewModel: MainContainerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeaturesViewModel::class)
    fun bindFeaturesViewModel(viewModel: FeaturesViewModel): ViewModel

    @FeatureScope
    @Binds
    fun provideNavControllerHolder(holder: NavControllerHolderImpl): NavControllerHolder

    @FeatureScope
    @Binds
    fun bindRouter(holder: FeatureRouterImp): NavRouter
}
