package com.example.aheena.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.scope.ApplicationScope
import com.example.core.utils.connectivity_checker.ConnectivityChecker
import com.example.core.utils.coroutines.AppCoroutineScope
import com.example.core.utils.eventbus.AppEventBus
import com.example.core.utils.shared_preferences.AndroidPreferencesProvider
import com.example.core.utils.shared_preferences.EncryptedSharedPreferencesProvider
import com.example.core.utils.string_provider.StringProvider
import com.example.core.utils.time.AppSystemClock
import com.example.core.utils.view_model_factory.AppViewModelFactory
import com.example.core_impl.connectivity_checker.ConnectivityCheckerImpl
import com.example.core_impl.utils.coroutine.AppCoroutineScopeImpl
import com.example.core_impl.utils.eventbus.AppEventBusImpl
import com.example.core_impl.utils.shared_preferences.AndroidPreferencesProviderImpl
import com.example.core_impl.utils.shared_preferences.EncryptedSharedPreferencesProviderImpl
import com.example.core_impl.utils.string_provider.StringProviderImpl
import com.example.core_impl.utils.time.AppSystemClockImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface UtilsModule {

    @Binds
    fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun provideStringProvider(stringProvider: StringProviderImpl): StringProvider

    @Binds
    fun provideSystemClock(systemClock: AppSystemClockImpl): AppSystemClock

    @ApplicationScope
    @Binds
    fun provideEncryptedSharedPreferencesProvider(provider: EncryptedSharedPreferencesProviderImpl): EncryptedSharedPreferencesProvider

    @ApplicationScope
    @Binds
    fun provideAndroidPreferencesProvider(provider: AndroidPreferencesProviderImpl): AndroidPreferencesProvider

    @Binds
    fun provideConnectivityChecker(provider: ConnectivityCheckerImpl): ConnectivityChecker

    @ApplicationScope
    @Binds
    fun provideAppCoroutineScope(provider: AppCoroutineScopeImpl): AppCoroutineScope

    @ApplicationScope
    @Binds
    fun provideAppEventBus(eventBus: AppEventBusImpl): AppEventBus

    companion object {
        @Provides
        fun provideGson(): Gson = GsonBuilder().disableHtmlEscaping().setLenient().create()
    }
}
