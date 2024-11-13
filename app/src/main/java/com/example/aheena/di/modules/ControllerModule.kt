package com.example.aheena.di.modules

import com.example.core_api.controller.logout.LogoutController
import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.controller.theme.ThemeManager
import com.example.core_api.di.scope.ApplicationScope
import com.example.core_impl.controller.logout.LogoutControllerImpl
import com.example.core_impl.controller.session.UserSessionControllerImpl
import com.example.core_impl.controller.theme.ThemeManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface ControllerModule {

    @ApplicationScope
    @Binds
    fun bindThemeManager(themeManager: ThemeManagerImpl): ThemeManager

    @ApplicationScope
    @Binds
    fun bindLogoutController(logoutController: LogoutControllerImpl): LogoutController

    @ApplicationScope
    @Binds
    fun bindUserSessionController(userSessionController: UserSessionControllerImpl): UserSessionController
}
