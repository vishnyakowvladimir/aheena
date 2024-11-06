package com.example.feature_main.presentation.features.mvi.handler

import com.example.feature_main.presentation.features.mvi.model.FeaturesEvent
import com.example.feature_main.presentation.features.mvi.model.FeaturesSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class FeaturesSideEffectHandler @Inject constructor(
    private val uiHandler: FeaturesUiSideEffectHandler,
    private val domainHandler: FeaturesDomainSideEffectHandler,
) : SideEffectHandler<FeaturesEvent, FeaturesSideEffect> {

    override fun getEventSource(): Flow<FeaturesEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: FeaturesSideEffect) {
        when (sideEffect) {
            is FeaturesSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is FeaturesSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
            else -> Unit
        }
    }
}
