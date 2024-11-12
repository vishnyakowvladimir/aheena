package com.example.core.network.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val COMPLETABLE_RESULT = ApiResult.Success(Completable)

object Completable : Any()

sealed class ApiResult<out T> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val error: Throwable, val cachedData: Any? = null) : ApiResult<Nothing>()

    fun getDataIfSuccess(): T? {
        return if (this is Success) data else null
    }
}

fun ApiResult<*>.toCompletableResult() = when (this) {
    is ApiResult.Success<*> -> COMPLETABLE_RESULT
    is ApiResult.Error -> this
}

fun <T : Any> T.toApiResult(): ApiResult<T> {
    return when {
        this !is Exception -> ApiResult.Success(this)
        else -> ApiResult.Error(error = this)
    }
}

inline fun <Dto : Any, Domain : Any> ApiResult<Dto>.mapDtoToDomain(
    mapper: (Dto) -> Domain,
): ApiResult<Domain> = when (this) {
    is ApiResult.Success -> ApiResult.Success(mapper(data))
    is ApiResult.Error -> this
}

inline fun <Dto : Any, Domain : Any> Flow<ApiResult<Dto>>.mapDtoToDomain(
    crossinline mapper: (Dto) -> Domain,
): Flow<ApiResult<Domain>> {
    return this.map { result ->
        when (result) {
            is ApiResult.Success -> ApiResult.Success(mapper(result.data))
            is ApiResult.Error -> result
        }
    }
}

fun Throwable.toResultError() = ApiResult.Error(this)
