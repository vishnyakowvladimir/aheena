package com.example.feature_main.presentation.main.mvi.reducer

import com.example.core_api.pending_navigation.model.PendingNavigationState
import com.example.feature_main.presentation.main.mvi.model.MainDomainState
import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.main.mvi.model.MainSideEffect
import com.example.feature_main.presentation.main.mvi.model.MainUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainUiReducer @Inject constructor() :
    Reducer<MainEvent.Ui, MainDomainState, MainSideEffect, MainUiCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent.Ui,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return when (event) {
            is MainEvent.Ui.OnBackPressed -> reduceOnBackPressed()
            is MainEvent.Ui.OnBottomBarItemClick -> reduceOnBottomBarItemClick(state, event)
            is MainEvent.Ui.OnPendingNavigation -> reduceOnPendingNavigation(event)
        }
    }

    private fun reduceOnBackPressed(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Ui.Back),
        )
    }

    private fun reduceOnBottomBarItemClick(
        state: MainDomainState,
        event: MainEvent.Ui.OnBottomBarItemClick,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        val updatedBottomBarState = state.bottomBarState.updateStateByIndex(selectedIndex = event.index)

        val currentPagerState = state.pagerState
        val updatedPagerState = currentPagerState.copy(
            currentPage = event.index,
        )

        return Update.state(
            state.copy(
                bottomBarState = updatedBottomBarState,
                pagerState = updatedPagerState,
            )
        )
    }

    private fun reduceOnPendingNavigation(
        event: MainEvent.Ui.OnPendingNavigation,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return when (event.state) {
            is PendingNavigationState.None -> {
                Update.nothing()
            }

            is PendingNavigationState.Action -> {
                Update.sideEffects(
                    sideEffects = listOf(MainSideEffect.Ui.PendingNavigationAction(event.state)),
                )
            }
        }
    }
}
