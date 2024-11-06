package com.example.feature_main.presentation.features.mvi.reducer

import com.example.feature_main.presentation.features.mvi.model.FeaturesDomainState
import com.example.feature_main.presentation.features.mvi.model.FeaturesEvent
import com.example.feature_main.presentation.features.mvi.model.FeaturesSideEffect
import com.example.feature_main.presentation.features.mvi.model.FeaturesUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class FeaturesDomainReducer @Inject constructor() :
    Reducer<FeaturesEvent.Domain, FeaturesDomainState, FeaturesSideEffect, FeaturesUiCommand> {

    override fun update(
        state: FeaturesDomainState,
        event: FeaturesEvent.Domain,
    ): Update<FeaturesDomainState, FeaturesSideEffect, FeaturesUiCommand> {
        return Update.nothing()
    }
}

