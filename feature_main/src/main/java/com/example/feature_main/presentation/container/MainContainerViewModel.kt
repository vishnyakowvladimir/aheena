package com.example.feature_main.presentation.container

import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.presentation.base.BaseViewModel
import javax.inject.Inject

class MainContainerViewModel @Inject constructor(
    userSessionController: UserSessionController,
) : BaseViewModel() {

    init {
        userSessionController.enable()
    }
}
