package com.example.aheena.di.modules

import com.example.core.cache.data_source.CacheDataSource
import com.example.core.cache.storage.CacheStorage
import com.example.core.di.scope.ApplicationScope
import com.example.core_impl.cache.data_source.CacheDataSourceImpl
import com.example.core_impl.cache.storage.CacheStorageImpl
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
