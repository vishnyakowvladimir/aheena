package com.example.core_impl.presentation.theme_manager

import androidx.appcompat.app.AppCompatDelegate
import com.example.core.presentation.theme_manager.ThemeManager
import com.example.core_impl.holder.ActivityHolder
import com.example.data_sdk_api.interactor.ThemeInteractor
import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale
import javax.inject.Inject

class ThemeManagerImpl @Inject constructor(
    private val themeInteractor: ThemeInteractor,
    private val activityHolder: ActivityHolder,
) : ThemeManager {

    override fun applyThemeModeLight() {
        activityHolder.activity?.let { activity ->
            AppCompatDelegate.setDefaultNightMode(AppThemeMode.LIGHT.mode)
            activity.delegate.applyDayNight()
        }
    }

    override fun applyThemeModeDark() {
        activityHolder.activity?.let { activity ->
            AppCompatDelegate.setDefaultNightMode(AppThemeMode.DARK.mode)
            activity.delegate.applyDayNight()
        }
    }

    override fun applyThemeModeSystem() {
        activityHolder.activity?.let { activity ->
            AppCompatDelegate.setDefaultNightMode(AppThemeMode.SYSTEM.mode)
            activity.delegate.applyDayNight()
        }
    }

    override fun saveScale(scale: ViewScale) {
        themeInteractor.saveScale(scale.mapToDomain())
    }

    override fun getScale(): ViewScale {
        return themeInteractor.getScale().mapToUi()
    }

    override fun getThemeMode(): AppThemeMode {
        return themeInteractor.getThemeMode().mapToUi()
    }

    private fun saveThemeMode(themeMode: AppThemeMode) {
        themeInteractor.saveThemeMode(themeMode.mapToDomain())
    }
}

private fun ViewScaleDomain.mapToUi(): ViewScale {
    return when (this) {
        ViewScaleDomain.M -> ViewScale.M
        ViewScaleDomain.L -> ViewScale.L
        ViewScaleDomain.XL -> ViewScale.XL
    }
}

private fun ViewScale.mapToDomain(): ViewScaleDomain {
    return when (this) {
        ViewScale.M -> ViewScaleDomain.M
        ViewScale.L -> ViewScaleDomain.L
        ViewScale.XL -> ViewScaleDomain.XL
    }
}

private fun AppThemeModeDomain.mapToUi(): AppThemeMode {
    return when (this) {
        AppThemeModeDomain.LIGHT -> AppThemeMode.LIGHT
        AppThemeModeDomain.DARK -> AppThemeMode.DARK
        AppThemeModeDomain.SYSTEM -> AppThemeMode.SYSTEM
    }
}

private fun AppThemeMode.mapToDomain(): AppThemeModeDomain {
    return when (this) {
        AppThemeMode.LIGHT -> AppThemeModeDomain.LIGHT
        AppThemeMode.DARK -> AppThemeModeDomain.DARK
        AppThemeMode.SYSTEM -> AppThemeModeDomain.SYSTEM
    }
}
