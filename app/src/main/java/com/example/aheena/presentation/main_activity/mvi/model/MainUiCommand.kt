package com.example.aheena.presentation.main_activity.mvi.model

internal sealed interface MainUiCommand {
    data class ShowSnackbar(val text: String) : MainUiCommand
}
