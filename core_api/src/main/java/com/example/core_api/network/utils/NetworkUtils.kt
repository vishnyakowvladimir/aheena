package com.example.core_api.network.utils

import com.example.core_api.network.model.ApiResult
import com.example.core_api.network.model.toApiResult
import com.example.core_api.network.model.toResultError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

fun <Dto : Any> sendRequest(
    request: suspend () -> Dto,
): Flow<ApiResult<Dto>> {
    return flow {
        val result = request().toApiResult()

        emit(result)
    }
        .catch { throwable ->
            emit(throwable.toResultError())
        }
}
