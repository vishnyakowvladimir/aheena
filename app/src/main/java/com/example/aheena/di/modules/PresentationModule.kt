package com.example.aheena.di.modules

import com.example.core.di.scope.ApplicationScope
import com.example.core.holder.ActivityHolder
import com.example.core.presentation.theme_manager.ThemeManager
import com.example.core_impl.holder.ActivityHolderImpl
import com.example.core_impl.presentation.theme_manager.ThemeManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface PresentationModule {

    @ApplicationScope
    @Binds
    fun bindThemeManager(themeManager: ThemeManagerImpl): ThemeManager

    companion object {

        @ApplicationScope
        @Provides
        fun provideActivityHolder(): ActivityHolder {
            return ActivityHolderImpl()
        }
    }
}
