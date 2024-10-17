package com.example.aheena.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.aheena.di.AppComponent
import com.example.aheena.navigation.FeatureComposablesHolder
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
    lateinit var composablesHolder: FeatureComposablesHolder

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

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
                    composablesHolder = composablesHolder,
                )
            }
        }
    }
}
