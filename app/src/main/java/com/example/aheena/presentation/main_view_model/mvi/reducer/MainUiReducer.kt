package com.example.aheena.presentation.main_view_model.mvi.reducer

import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainUiReducer @Inject constructor() :
    Reducer<MainEvent.Ui, MainDomainState, MainSideEffect> {

    override fun update(
        state: MainDomainState,
        event: MainEvent.Ui,
    ): Update<MainDomainState, MainSideEffect> {
        return when (event) {
            is MainEvent.Ui.None -> Update.nothing()
            is MainEvent.Ui.OnBackPressed -> reduceOnBackPressed()
        }
    }

    private fun reduceOnBackPressed(): Update<MainDomainState, MainSideEffect> {
        return Update.sideEffects(MainSideEffect.Ui.Back)
    }
}
