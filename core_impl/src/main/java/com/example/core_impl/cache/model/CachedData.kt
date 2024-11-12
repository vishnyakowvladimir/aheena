package com.example.core_impl.cache.model

internal data class CachedData<T: Any>(
    val data: T,
    val time: Long,
)
