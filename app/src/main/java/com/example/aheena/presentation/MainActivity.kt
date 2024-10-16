package com.example.aheena.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.example.aheena.di.AppComponent
import com.example.aheena.navigation.FeatureComposableHolder
import com.example.core.di.extension.getComponent
import com.example.core.presentation.base.BaseActivity
import com.example.lib_ui.containers.MainContainer
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale
import javax.inject.Inject

internal class MainActivity : BaseActivity() {

    @Inject
    lateinit var navController: NavHostController

    @Inject
    lateinit var mediatorsHolder: FeatureComposableHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<AppComponent>().inject(this)

        super.onCreate(savedInstanceState)
        setContent {
            val viewScale = ViewScale.M
            val themeMode = AppThemeMode.LIGHT

            MainContainer(
                activity = this,
                viewScale = viewScale,
                themeMode = themeMode,
            ) {
                AppNavGraph(
                    navController = navController,
                    mediatorsHolder = mediatorsHolder,
                )
            }
        }
    }
}
