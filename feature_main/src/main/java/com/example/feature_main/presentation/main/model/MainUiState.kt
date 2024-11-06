package com.example.feature_main.presentation.main.model

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Immutable
import com.example.lib_ui.components.bottom_bar.model.AppBottomBarState

data class MainUiState(
    val bottomBarState: AppBottomBarState,
    val pagerState: PagerState,
) {

    @Immutable
    data class UiPagerState(
        val page: Int,
        override val pageCount: Int
    ) : PagerState(currentPage = page)
}
