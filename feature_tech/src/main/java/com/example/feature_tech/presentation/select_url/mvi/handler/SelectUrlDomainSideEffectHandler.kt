package com.example.feature_tech.presentation.select_url.mvi.handler

import com.example.data_source_api.storage.network.UrlProvider
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlEvent
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class SelectUrlDomainSideEffectHandler @Inject constructor(
    private val urlProvider: UrlProvider,
) :
    SideEffectHandler<SelectUrlEvent, SelectUrlSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<SelectUrlSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<SelectUrlEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: SelectUrlSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<SelectUrlEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is SelectUrlSideEffect.Domain.SaveUrlsType -> handleSaveUrlsType(sideEffect)
            }
        }
            .flowOn(Dispatchers.IO)
    }

    private fun handleSaveUrlsType(
        sideEffect: SelectUrlSideEffect.Domain.SaveUrlsType,
    ): Flow<SelectUrlEvent> {
        return flow {
            urlProvider.selectedUrlsType = sideEffect.urlsType
            emit(SelectUrlEvent.None)
        }
    }
}

