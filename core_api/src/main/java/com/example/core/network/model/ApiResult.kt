package com.example.core.network.model

val COMPLETABLE_RESULT = ApiResult.Success(Completable)

object Completable : Any()

sealed interface ApiResult<out T> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>
    data class Error(val error: Throwable, val cachedData: Any? = null) : ApiResult<Nothing>
}

fun ApiResult<*>.toCompletableResult() = when (this) {
    is ApiResult.Success<*> -> COMPLETABLE_RESULT
    is ApiResult.Error -> this
}

inline fun <reified T : Any> T.toApiResult(): ApiResult<T> {
    return when {
        this !is Exception -> ApiResult.Success(this)
        else -> ApiResult.Error(error = this)
    }
}

inline fun <T : Any, R : Any> ApiResult<T>.mapResultSuccess(mapper: (T) -> R): ApiResult<R> = when (this) {
    is ApiResult.Success -> ApiResult.Success(mapper(data))
    is ApiResult.Error -> this
}

fun Throwable.toResultError() = ApiResult.Error(this)
