package com.example.aheena.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.core.presentation.base.BaseActivity
import com.example.lib_ui.containers.MainContainer
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale

internal class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewScale = ViewScale.M
            val themeMode = AppThemeMode.LIGHT

            MainContainer(
                activity = this,
                viewScale = viewScale,
                themeMode = themeMode,
            ) {
                AppNavGraph()
            }
        }
    }
}
