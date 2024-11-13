package com.example.feature_main.presentation.main.mvi.reducer

import com.example.core_api.utils.string_provider.StringProvider
import com.example.feature_main.R
import com.example.feature_main.presentation.main.mvi.model.MainDomainState
import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.main.mvi.model.MainSideEffect
import com.example.feature_main.presentation.main.mvi.model.MainUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject
import com.example.lib_ui.R as LibUiR

internal class MainReducer @Inject constructor(
    private val uiReducer: MainUiReducer,
    private val domainReducer: MainDomainReducer,
    private val stringProvider: StringProvider,
) : Reducer<MainEvent, MainDomainState, MainSideEffect, MainUiCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return when (event) {
            is MainEvent.None -> Update.nothing()
            is MainEvent.Ui -> uiReducer.update(state, event)
            is MainEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): MainDomainState {
        return MainDomainState(
            bottomBarState = MainDomainState.BottomBarState(
                items = listOf(
                    MainDomainState.BottomBarState.BottomBarItemState(
                        title = stringProvider.getString(R.string.main_bottom_bar_features),
                        iconRes = LibUiR.drawable.ic_24dp_city_bank,
                        isSelected = true,
                    ),
                    MainDomainState.BottomBarState.BottomBarItemState(
                        title = stringProvider.getString(R.string.main_bottom_bar_settings),
                        iconRes = LibUiR.drawable.ic_24dp_actions_settings,
                    ),
                ),
            ),
            pagerState = MainDomainState.PagerState(
                currentPage = 0,
            ),
        )
    }
}
