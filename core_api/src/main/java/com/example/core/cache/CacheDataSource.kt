package com.example.core.cache

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
