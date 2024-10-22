package com.example.aheena.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.scope.ApplicationScope
import com.example.core.utils.StringProvider
import com.example.core.utils.shared_preferences.AndroidPreferencesProvider
import com.example.core.utils.shared_preferences.EncryptedSharedPreferencesProvider
import com.example.core_impl.utils.AppViewModelFactory
import com.example.core_impl.utils.StringProviderImpl
import com.example.core_impl.utils.shared_preferences.AndroidPreferencesProviderImpl
import com.example.core_impl.utils.shared_preferences.EncryptedSharedPreferencesProviderImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UtilsModule {

    @Binds
    fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun provideStringProvider(stringProvider: StringProviderImpl): StringProvider

    @ApplicationScope
    @Binds
    fun provideEncryptedSharedPreferencesProvider(provider: EncryptedSharedPreferencesProviderImpl): EncryptedSharedPreferencesProvider

    @ApplicationScope
    @Binds
    fun provideAndroidPreferencesProvider(provider: AndroidPreferencesProviderImpl): AndroidPreferencesProvider
}
