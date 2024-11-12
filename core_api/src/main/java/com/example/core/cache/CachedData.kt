package com.example.core.cache

data class CachedData<T: Any>(
    val data: T,
    val time: Long,
)
