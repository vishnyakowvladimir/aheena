package com.example.feature_main.presentation.features.mvi.reducer

import com.example.core_api.navigation.feature_destination.FeaturesDestination
import com.example.feature_main.presentation.features.model.FeatureScreen
import com.example.feature_main.presentation.features.mvi.model.FeaturesDomainState
import com.example.feature_main.presentation.features.mvi.model.FeaturesEvent
import com.example.feature_main.presentation.features.mvi.model.FeaturesSideEffect
import com.example.feature_main.presentation.features.mvi.model.FeaturesUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class FeaturesUiReducer @Inject constructor() :
    Reducer<FeaturesEvent.Ui, FeaturesDomainState, FeaturesSideEffect, FeaturesUiCommand> {

    override fun update(
        state: FeaturesDomainState,
        event: FeaturesEvent.Ui,
    ): Update<FeaturesDomainState, FeaturesSideEffect, FeaturesUiCommand> {
        return when (event) {
            is FeaturesEvent.Ui.OnItemClick -> reduceOnItemClick(event)
        }
    }

    private fun reduceOnItemClick(
        event: FeaturesEvent.Ui.OnItemClick,
    ): Update<FeaturesDomainState, FeaturesSideEffect, FeaturesUiCommand> {
        val destination = when (event.featureScreen) {
            FeatureScreen.ITUNES -> FeaturesDestination.ItunesDestination
        }

        return Update.sideEffects(listOf(FeaturesSideEffect.Ui.OpenFeature(destination)))
    }
}
