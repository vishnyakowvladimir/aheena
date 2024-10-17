package com.example.aheena.presentation.main_view_model.mvi.model

internal sealed interface MainUiCommand {

    sealed interface Command : MainUiCommand

    sealed interface Navigation : MainUiCommand {
        data object Back : Navigation
    }
}
