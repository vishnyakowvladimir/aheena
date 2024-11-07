package com.example.feature_main.presentation.features.mvi.reducer

import com.example.feature_main.presentation.features.mvi.model.FeaturesDomainState
import com.example.feature_main.presentation.features.mvi.model.FeaturesEvent
import com.example.feature_main.presentation.features.mvi.model.FeaturesSideEffect
import com.example.feature_main.presentation.features.mvi.model.FeaturesUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class FeaturesReducer @Inject constructor(
    private val uiReducer: FeaturesUiReducer,
    private val domainReducer: FeaturesDomainReducer,
) : Reducer<FeaturesEvent, FeaturesDomainState, FeaturesSideEffect, FeaturesUiCommand> {

    override fun update(
        state: FeaturesDomainState,
        event: FeaturesEvent,
    ): Update<FeaturesDomainState, FeaturesSideEffect, FeaturesUiCommand> {
        return when (event) {
            is FeaturesEvent.None -> Update.nothing()
            is FeaturesEvent.Ui -> uiReducer.update(state, event)
            is FeaturesEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): FeaturesDomainState {
        return FeaturesDomainState()
    }
}
