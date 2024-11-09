package com.example.aheena.presentation.main_view_model.mvi.model

internal sealed interface MainUiCommand {
    data class ShowSnackbar(val text: String) : MainUiCommand
}
