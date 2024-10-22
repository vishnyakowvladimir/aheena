package com.example.aheena.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.core.utils.StringProvider
import com.example.core_impl.utils.AppViewModelFactory
import com.example.core_impl.utils.StringProviderImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UtilsModule {

    @Binds
    fun provideStringProvider(stringProvider: StringProviderImpl): StringProvider

    @Binds
    fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
