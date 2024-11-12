package com.example.aheena.di.modules

import com.example.core.cache.CacheDataSource
import com.example.core.cache.CacheStorage
import com.example.core.di.scope.ApplicationScope
import com.example.core_impl.cache.CacheDataSourceImpl
import com.example.core_impl.cache.CacheStorageImpl
import dagger.Binds
import dagger.Module

@Module
interface CacheModule {

    @ApplicationScope
    @Binds
    fun bindCacheStorage(storage: CacheStorageImpl): CacheStorage

    @Binds
    fun bindCacheDataSource(source: CacheDataSourceImpl): CacheDataSource
}
