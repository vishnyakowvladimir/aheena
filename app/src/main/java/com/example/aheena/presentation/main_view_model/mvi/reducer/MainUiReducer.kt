package com.example.aheena.presentation.main_view_model.mvi.reducer

import com.example.aheena.presentation.main_view_model.mvi.model.MainCommand
import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainUiReducer @Inject constructor() :
    Reducer<MainEvent.Ui, MainDomainState, MainSideEffect, MainCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent.Ui,
    ): Update<MainDomainState, MainSideEffect, MainCommand> {
        return when (event) {
            is MainEvent.Ui.OnBackPressed -> reduceOnBackPressed()
        }
    }

    private fun reduceOnBackPressed(): Update<MainDomainState, MainSideEffect, MainCommand> {
        return Update.sideEffects(MainSideEffect.Back)
    }
}
