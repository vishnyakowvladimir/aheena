package com.example.aheena.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.core.utils.StringProvider
import com.example.core.utils.StringProviderImpl
import com.example.core_impl.presentation.AppViewModelFactory
import dagger.Binds
import dagger.Module

@Module
internal interface UtilsModule {

    @Binds
    fun provideStringProvider(stringProvider: StringProviderImpl): StringProvider

    @Binds
    fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
