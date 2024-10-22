package com.example.core.utils.shared_preferences

import android.content.SharedPreferences

interface AndroidPreferencesProvider {

    val prefs: SharedPreferences

    val cryptoPrefs: SharedPreferences
}
