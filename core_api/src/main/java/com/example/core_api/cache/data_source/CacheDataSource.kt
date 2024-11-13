package com.example.core_api.cache.data_source

import com.example.core_api.cache.model.CachedDataKey
import com.example.core_api.network.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface CacheDataSource {

    fun <Dto : Any> get(
        key: CachedDataKey,
        postfixKey: String,
        needUpdate: Boolean = false,
        request: suspend () -> Dto,
    ): Flow<ApiResult<Dto>>
}
