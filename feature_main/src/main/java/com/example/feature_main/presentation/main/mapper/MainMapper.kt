package com.example.feature_main.presentation.main.mapper

import com.example.feature_main.presentation.main.model.MainUiState
import com.example.feature_main.presentation.main.mvi.model.MainDomainState
import com.example.lib_ui.components.bottom_bar.model.AppBottomBarItemState
import com.example.lib_ui.components.bottom_bar.model.AppBottomBarState
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class MainMapper @Inject constructor() {
    fun map(domainState: MainDomainState): MainUiState {
        return MainUiState(
            bottomBarState = mapBottomBarState(domainState.bottomBarState),
        )
    }

    private fun mapBottomBarState(domainState: MainDomainState.BottomBarState): AppBottomBarState {
        return AppBottomBarState(
            items = domainState.items.map { item ->
                mapBottomBarItemState(item)
            }.toImmutableList()
        )
    }

    private fun mapBottomBarItemState(
        domainState: MainDomainState.BottomBarState.BottomBarItemState,
    ): AppBottomBarItemState {
        return AppBottomBarItemState(
            title = domainState.title,
            iconRes = domainState.iconRes,
            isSelected = domainState.isSelected,
        )
    }
}
