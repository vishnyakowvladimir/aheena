package com.example.core.network.utils

import com.example.core.network.model.ApiResult
import com.example.core.network.model.mapResultSuccess
import com.example.core.network.model.toApiResult
import com.example.core.network.model.toResultError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

inline fun <reified Dto : Any, reified Domain : Any> sendRequest(
    crossinline mapper: (Dto) -> Domain,
    crossinline request: suspend () -> Dto,
): Flow<ApiResult<Domain>> {
    return flow {
        val result = request()
            .toApiResult()
            .mapResultSuccess(mapper)

        emit(result)
    }
        .catch { throwable ->
            emit(throwable.toResultError())
        }
}
