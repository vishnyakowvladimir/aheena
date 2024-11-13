package com.example.core_api.utils.shared_preferences

import android.content.SharedPreferences

interface AndroidPreferencesProvider {

    val prefs: SharedPreferences

    val cryptoPrefs: SharedPreferences
}
