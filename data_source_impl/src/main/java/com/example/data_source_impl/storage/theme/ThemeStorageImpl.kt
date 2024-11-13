package com.example.data_source_impl.storage.theme

import androidx.core.content.edit
import com.example.core_api.utils.shared_preferences.AndroidPreferencesProvider
import com.example.data_source_api.models.theme.AppThemeModeDto
import com.example.data_source_api.models.theme.ViewScaleDto
import com.example.data_source_api.storage.theme.ThemeStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

private const val KEY_SCALE = "KEY_SCALE"
private const val KEY_THEME_MODE = "KEY_THEME_MODE"

class ThemeStorageImpl @Inject constructor(
    private val gson: Gson,
    provider: AndroidPreferencesProvider,
) : ThemeStorage {
    private val pref = provider.prefs

    override fun saveViewScale(scale: ViewScaleDto) {
        pref.edit {
            putString(KEY_SCALE, gson.toJson(scale))
        }
    }

    override fun getViewScale(): ViewScaleDto {
        return pref.getString(KEY_SCALE, "")?.let { json ->
            gson.fromJson(
                json,
                object : TypeToken<ViewScaleDto>() {}.type
            )
        } ?: ViewScaleDto.M
    }

    override fun saveThemeMode(mode: AppThemeModeDto) {
        pref.edit {
            putString(KEY_THEME_MODE, gson.toJson(mode))
        }
    }

    override fun getThemeMode(): AppThemeModeDto {
        return pref.getString(KEY_THEME_MODE, "")?.let { json ->
            gson.fromJson(
                json,
                object : TypeToken<AppThemeModeDto>() {}.type
            )
        } ?: AppThemeModeDto.LIGHT
    }
}
