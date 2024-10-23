package com.example.aheena.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import com.example.aheena.di.AppComponent
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.aheena.presentation.compose.MainContainer
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.core.di.extension.getComponent
import com.example.core.presentation.base.BaseActivity
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.core_impl.navigation.NavControllerHolder
import com.example.lib_ui.theme.AppThemeContainer
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale
import javax.inject.Inject

internal class MainActivity : BaseActivity() {

    @Inject
    lateinit var navControllerHolder: NavControllerHolder

    @Inject
    lateinit var composablesHolder: FeatureComposablesHolder

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<AppComponent>().inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            val state = mainViewModel.uiState.collectAsStateLifecycleAware()

            val themeState = state.value.themeState

            applyThemeMode(
                activity = this,
                themeMode = themeState?.themeMode ?: AppThemeMode.LIGHT,
            )

            AppThemeContainer(viewScale = themeState?.viewScale ?: ViewScale.M) {
                Surface {
                    MainContainer(
                        viewModelFactory = viewModelFactory,
                        navControllerHolder = navControllerHolder,
                        composablesHolder = composablesHolder,
                        mainViewModel = mainViewModel,
                    )
                }
            }
        }
    }
}

private fun applyThemeMode(
    activity: AppCompatActivity,
    themeMode: AppThemeMode,
) {
    AppCompatDelegate.setDefaultNightMode(themeMode.mode)
    activity.delegate.applyDayNight()
}
