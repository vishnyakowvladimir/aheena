package com.example.core_impl.cache.data_source

import com.example.core_api.cache.data_source.CacheDataSource
import com.example.core_api.cache.model.CachedDataKey
import com.example.core_api.network.model.ApiResult
import com.example.core_api.network.utils.sendRequest
import com.example.core_api.cache.storage.CacheStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(
    private val cacheStorage: CacheStorage,
) : CacheDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    override fun <Dto : Any> get(
        key: CachedDataKey,
        postfixKey: String,
        needUpdate: Boolean,
        request: suspend () -> Dto,
    ): Flow<ApiResult<Dto>> {
        return when {
            needUpdate -> {
                sendRequestAndSaveCache(
                    key = key,
                    postfixKey = postfixKey,
                    request = request,
                )
            }

            else -> {
                flow {
                    val dto = cacheStorage.get(
                        key = key,
                        postfixKey = postfixKey,
                    ) as? Dto

                    emit(dto)
                }
                    .flatMapLatest { dto ->
                        if (dto != null) {
                            flow { emit(ApiResult.Success(dto)) }
                        } else {
                            sendRequestAndSaveCache(
                                key = key,
                                postfixKey = postfixKey,
                                request = request,
                            )
                        }
                    }
            }
        }
    }

    private fun <Dto : Any> sendRequestAndSaveCache(
        key: CachedDataKey,
        postfixKey: String,
        request: suspend () -> Dto,
    ): Flow<ApiResult<Dto>> {
        return sendRequest(request)
            .onEach { result ->
                result.getDataIfSuccess()?.let { data ->
                    cacheStorage.save(
                        data = data,
                        key = key,
                        postfixKey = postfixKey,
                    )

                }
            }
    }
}
