package com.example.core.cache.data_source

import com.example.core.cache.model.CachedDataKey
import com.example.core.network.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface CacheDataSource {

    fun <Dto : Any> get(
        key: CachedDataKey,
        postfixKey: String,
        needUpdate: Boolean = false,
        request: suspend () -> Dto,
    ): Flow<ApiResult<Dto>>
}
