package com.example.aheena.presentation.main_view_model.mapper

import com.example.aheena.presentation.main_view_model.mapper.model.MainUiState
import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import javax.inject.Inject

internal class MainMapper @Inject constructor() {

    fun map(domainState: MainDomainState): MainUiState {
        return MainUiState(
            none = domainState.none,
        )
    }
}
