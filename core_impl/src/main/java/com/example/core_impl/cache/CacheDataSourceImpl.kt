package com.example.core_impl.cache

import com.example.core.cache.CacheDataSource
import com.example.core.cache.CacheStorage
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(
    private val cacheStorage: CacheStorage,
) : CacheDataSource {

//    suspend fun <T : Any> get(
//        key: CachedDataKey,
//        postfixKey: String,
//        needUpdate: Boolean = false,
//    ): ApiResult<T> {
//        return when {
//            needUpdate -> {
//
//            }
//
//            else -> {
//
//            }
//        }
//    }
}
