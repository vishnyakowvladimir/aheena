package com.example.aheena.presentation

import com.example.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {
    private val _uiState = MutableStateFlow(true)
    val uiState = _uiState.asStateFlow()
}
