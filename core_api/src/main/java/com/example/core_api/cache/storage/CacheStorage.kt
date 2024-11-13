package com.example.core_api.cache.storage

import com.example.core_api.cache.model.CachedDataKey

private const val POSTFIX_KEY_DEFAULT = "POSTFIX_KEY_DEFAULT"

interface CacheStorage {

    fun <T : Any> save(
        data: T,
        key: CachedDataKey,
        postfixKey: String = POSTFIX_KEY_DEFAULT,
    )

    fun get(
        key: CachedDataKey,
        postfixKey: String = POSTFIX_KEY_DEFAULT,
    ): Any?

    fun clear(key: CachedDataKey)

    fun clearAll()
}
