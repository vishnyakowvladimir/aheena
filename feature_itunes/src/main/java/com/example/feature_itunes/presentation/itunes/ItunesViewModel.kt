package com.example.feature_itunes.presentation.itunes

import com.example.core.navigation.router.NavRouter
import com.example.core.presentation.base.BaseViewModel
import javax.inject.Inject

internal class ItunesViewModel @Inject constructor(
    private val router: NavRouter,
) : BaseViewModel() {

    fun onBackPressed() {
        router.popBackStack()
    }
}
