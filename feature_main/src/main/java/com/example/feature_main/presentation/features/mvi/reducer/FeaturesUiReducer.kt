package com.example.feature_main.presentation.features.mvi.reducer

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
        return Update.nothing()
    }
}
