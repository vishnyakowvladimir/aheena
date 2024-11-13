package com.example.core_impl.controller.theme

import androidx.appcompat.app.AppCompatDelegate
import com.example.core_api.holder.ActivityHolder
import com.example.core_api.controller.theme.ThemeManager
import com.example.core_api.controller.theme.mapToUi
import com.example.data_sdk_api.interactor.theme.ThemeInteractor
import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain
import com.example.lib_ui.theme.AppThemeMode
import javax.inject.Inject

class ThemeManagerImpl @Inject constructor(
    private val themeInteractor: ThemeInteractor,
    private val activityHolder: ActivityHolder,
) : ThemeManager {
    override fun applyThemeMode() {
        activityHolder.activity?.let { activity ->
            val mode = themeInteractor.getThemeMode().mapToUi()
            AppCompatDelegate.setDefaultNightMode(mode.mode)
            activity.delegate.applyDayNight()
        }
    }

    override fun applyThemeModeLight() {
        activityHolder.activity?.let { activity ->
            saveThemeMode(AppThemeModeDomain.LIGHT)
            AppCompatDelegate.setDefaultNightMode(AppThemeMode.LIGHT.mode)
            activity.delegate.applyDayNight()
        }
    }

    override fun applyThemeModeDark() {
        activityHolder.activity?.let { activity ->
            saveThemeMode(AppThemeModeDomain.DARK)
            AppCompatDelegate.setDefaultNightMode(AppThemeMode.DARK.mode)
            activity.delegate.applyDayNight()
        }
    }

    override fun applyThemeModeSystem() {
        activityHolder.activity?.let { activity ->
            saveThemeMode(AppThemeModeDomain.SYSTEM)
            AppCompatDelegate.setDefaultNightMode(AppThemeMode.SYSTEM.mode)
            activity.delegate.applyDayNight()
        }
    }

    override fun saveScale(scale: ViewScaleDomain) {
        themeInteractor.saveScale(scale)
    }

    override fun getScale(): ViewScaleDomain {
        return themeInteractor.getScale()
    }

    override fun getThemeMode(): AppThemeModeDomain {
        return themeInteractor.getThemeMode()
    }

    private fun saveThemeMode(themeMode: AppThemeModeDomain) {
        themeInteractor.saveThemeMode(themeMode)
    }
}
