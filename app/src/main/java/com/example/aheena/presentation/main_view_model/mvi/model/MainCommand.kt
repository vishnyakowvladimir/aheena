package com.example.aheena.presentation.main_view_model.mvi.model

internal sealed interface MainCommand {
    data object LoadAppTheme : MainCommand
}
