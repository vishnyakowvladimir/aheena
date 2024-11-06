package com.example.feature_main.presentation.main.mvi.model

import androidx.annotation.DrawableRes

internal data class MainDomainState(
    val bottomBarState: BottomBarState,
    val pagerState: PagerState,
) {
    data class BottomBarState(
        val items: List<BottomBarItemState>
    ) {

        data class BottomBarItemState(
            val title: String = "",
            @DrawableRes val iconRes: Int? = null,
            val isSelected: Boolean = false,
        )

        fun updateStateByIndex(selectedIndex: Int): BottomBarState {
            val updatedItems = items.mapIndexed { index, item ->
                item.copy(isSelected = index == selectedIndex)
            }

            return copy(items = updatedItems)
        }
    }

    data class PagerState(
        val currentPage: Int,
    )
}
