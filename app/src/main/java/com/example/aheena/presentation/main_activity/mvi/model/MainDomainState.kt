package com.example.aheena.presentation.main_activity.mvi.model

import android.net.Uri
import com.example.domain_models.theme.ViewScaleDomain

internal data class MainDomainState(
    val themeState: ThemeState,
    val data: String = "",
    val deeplinkState: DeeplinkState? = null,

    ) {
    data class ThemeState(
        val viewScale: ViewScaleDomain,
    )

    data class DeeplinkState(
        val uri: Uri,
    )
}
