package com.example.feature_tech.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core_api.di.key.ViewModelKey
import com.example.core_api.di.scope.FeatureScope
import com.example.core_api.navigation.router.FeatureRouterImp
import com.example.core_api.navigation.router.NavControllerHolder
import com.example.core_api.navigation.router.NavControllerHolderImpl
import com.example.core_api.navigation.router.NavRouter
import com.example.core_api.utils.view_model_factory.AppViewModelFactory
import com.example.feature_tech.presentation.container.TechContainerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface TechModule {

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
    @ViewModelKey(TechContainerViewModel::class)
    fun bindTechContainerViewModel(viewModel: TechContainerViewModel): ViewModel
}
