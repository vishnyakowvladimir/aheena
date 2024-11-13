package com.example.feature_main.presentation.features.mvi.handler

import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.router.NavRouter
import com.example.feature_main.presentation.features.mvi.model.FeaturesEvent
import com.example.feature_main.presentation.features.mvi.model.FeaturesSideEffect
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
internal class FeaturesUiSideEffectHandler @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
) : SideEffectHandler<FeaturesEvent, FeaturesSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<FeaturesSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<FeaturesEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: FeaturesSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<FeaturesEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is FeaturesSideEffect.Ui.OpenFeature -> handleOpenFeature(sideEffect)
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleOpenFeature(sideEffect: FeaturesSideEffect.Ui.OpenFeature): Flow<FeaturesEvent> {
        return flow {
            mainRouter.navigate(sideEffect.destination)
            emit(FeaturesEvent.None)
        }
    }
}

