package com.example.feature_main.presentation.main.mapper

import com.example.feature_main.presentation.main.model.MainUiState
import com.example.feature_main.presentation.main.mvi.model.MainDomainState
import javax.inject.Inject

internal class MainMapper @Inject constructor() {
    fun map(domainState: MainDomainState): MainUiState {
        return MainUiState(
            isLoading = domainState.isLoading,
        )
    }
}
