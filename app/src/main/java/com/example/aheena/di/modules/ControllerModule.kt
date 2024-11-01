package com.example.aheena.di.modules

import com.example.core.controller.logout.LogoutController
import com.example.core.controller.theme.ThemeManager
import com.example.core.di.scope.ApplicationScope
import com.example.core_impl.controller.logout.LogoutControllerImpl
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
}
