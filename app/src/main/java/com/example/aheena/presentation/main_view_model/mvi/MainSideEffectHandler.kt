package com.example.aheena.presentation.main_view_model.mvi

import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.aheena.presentation.main_view_model.mvi.model.MainUiCommand
import javax.inject.Inject

internal class MainSideEffectHandler @Inject constructor() {

    fun createUiSideEffect(sideEffect: MainSideEffect): MainUiCommand {
        return when (sideEffect) {
            is MainSideEffect.Back -> MainUiCommand.Navigation.Back
        }
    }
}
