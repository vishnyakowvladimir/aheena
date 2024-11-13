package com.example.feature_itunes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core_api.di.key.ViewModelKey
import com.example.core_api.di.scope.FeatureScope
import com.example.core_api.navigation.router.FeatureRouterImp
import com.example.core_api.navigation.router.NavControllerHolder
import com.example.core_api.navigation.router.NavControllerHolderImpl
import com.example.core_api.navigation.router.NavRouter
import com.example.core_api.utils.view_model_factory.AppViewModelFactory
import com.example.feature_itunes.presentation.container.ItunesContainerViewModel
import com.example.feature_itunes.presentation.itunes.ItunesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface ItunesModule {

    @Binds
    fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @FeatureScope
    @Binds
    fun bindNavControllerHolder(holder: NavControllerHolderImpl): NavControllerHolder

    @FeatureScope
    @Binds
    fun bindRouter(holder: FeatureRouterImp): NavRouter

    @Binds
    @IntoMap
    @ViewModelKey(ItunesContainerViewModel::class)
    fun bindItunesContainerViewModel(viewModel: ItunesContainerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ItunesViewModel::class)
    fun bindItunesViewModel(viewModel: ItunesViewModel): ViewModel
}
