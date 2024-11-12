package com.example.core_impl.cache

import com.example.core.cache.CacheStorage
import com.example.core.cache.CachedData
import com.example.core.cache.CachedDataKey
import com.example.core.utils.time.AppSystemClock
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class CacheStorageImpl @Inject constructor(
    private val clock: AppSystemClock,
) : CacheStorage {

    private val cache = ConcurrentHashMap<CachedDataKey, Map<String, CachedData<out Any>>>()

    override fun <T : Any> save(
        data: T,
        key: CachedDataKey,
        postfixKey: String,
    ) {
        val cachedData = CachedData(
            data = data,
            time = clock.getCurrentTimeMillis(),
        )

        val map = mapOf(postfixKey to cachedData)
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
