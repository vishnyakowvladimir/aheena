package com.example.core_api.utils.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transform

/**
 * Кастомный SharedFlow с однократной доставкой события подписчику.
 * Событие доставляется одному подписчику только один раз.
 * Отличие от Channel в том, что событие доставится, если подписка была сделана позже, чем отправлено событие.
 */
class OneTimeSharedFlow<T> {
    private val shared = MutableSharedFlow<T>(
        replay = 1,
        extraBufferCapacity = 0
    )

    private val consumed = MutableStateFlow(false)

    val flow: Flow<T> = shared
        .transform { value ->
            if (!consumed.value) {
                consumed.value = true
                emit(value)
            }
        }

    fun emit(value: T) {
        consumed.value = false
        shared.tryEmit(value)
    }
}
