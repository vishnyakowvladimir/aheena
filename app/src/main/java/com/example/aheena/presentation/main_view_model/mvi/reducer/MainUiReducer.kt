package com.example.aheena.presentation.main_view_model.mvi.reducer

import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.aheena.presentation.main_view_model.mvi.model.MainUiCommand
import com.example.core.utils.time.AppSystemClock
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainUiReducer @Inject constructor(
    val appSystemClock: AppSystemClock,
) :
    Reducer<MainEvent.Ui, MainDomainState, MainSideEffect, MainUiCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent.Ui,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return when (event) {
            is MainEvent.Ui.None -> Update.nothing()
            is MainEvent.Ui.OnBackPressed -> reduceOnBackPressed()
            is MainEvent.Ui.OnApplyThemeNeeded -> reduceOnApplyThemeNeeded()
            is MainEvent.Ui.OnThemeApplied -> reduceOnThemeApplied()
            is MainEvent.Ui.OnChangeScaleNeeded -> reduceOnChangeScaleNeeded(event)
            is MainEvent.Ui.OnTouch -> reduceOnTouch()
        }
    }

    private fun reduceOnBackPressed(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Ui.Back),
        )
    }

    private fun reduceOnApplyThemeNeeded(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Ui.ApplyTheme),
        )
    }

    private fun reduceOnThemeApplied(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Domain.LoadData),
        )
    }

    private fun reduceOnChangeScaleNeeded(
        event: MainEvent.Ui.OnChangeScaleNeeded,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Domain.ChangeViewScale(event.scale)),
        )
    }

    private fun reduceOnTouch(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Domain.SaveUserActivity(appSystemClock.getCurrentTimeMillis())),
        )
    }
}
