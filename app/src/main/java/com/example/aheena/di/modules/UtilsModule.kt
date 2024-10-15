package com.example.aheena.di.modules

import com.example.core.utils.StringProvider
import com.example.core.utils.StringProviderImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UtilsModule {

    @Binds
    fun provideStringProvider(stringProvider: StringProviderImpl): StringProvider
}
