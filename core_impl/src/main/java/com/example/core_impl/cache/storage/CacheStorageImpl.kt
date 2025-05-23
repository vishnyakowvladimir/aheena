package com.example.core_impl.cache.storage

import com.example.core_api.cache.model.CachedDataKey
import com.example.core_api.cache.storage.CacheStorage
import com.example.core_api.utils.time.AppSystemClock
import com.example.core_impl.cache.model.CachedData
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class CacheStorageImpl @Inject constructor(
    private val clock: AppSystemClock,
) : CacheStorage {

    private val cache = ConcurrentHashMap<CachedDataKey, MutableMap<String, CachedData<out Any>>>()

    override fun <T : Any> save(
        data: T,
        key: CachedDataKey,
        postfixKey: String,
    ) {
        val cachedData = CachedData(
            data = data,
            time = clock.getCurrentTimeMillis(),
        )

        val map = cache[key] ?: mutableMapOf()
        map[postfixKey] = cachedData
        cache[key] = map
    }

    override fun get(
        key: CachedDataKey,
        postfixKey: String,
    ): Any? {
        val cachedData = cache[key]?.get(postfixKey)
        return cachedData?.data
    }

    override fun clear(key: CachedDataKey) {
        cache.remove(key)
    }

    override fun clearAll() {
        cache.clear()
    }
}
