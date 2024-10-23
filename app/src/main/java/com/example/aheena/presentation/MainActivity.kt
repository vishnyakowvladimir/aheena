package com.example.aheena.presentation

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aheena.di.AppComponent
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.aheena.presentation.compose.AppNavGraph
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.core.di.extension.getComponent
import com.example.core.presentation.base.BaseActivity
import com.example.core.presentation.theme_manager.ThemeManager
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.core_impl.holder.ActivityHolder
import com.example.core_impl.holder.NavControllerHolder
import com.example.domain_models.theme.AppThemeModeDomain
import com.example.lib_ui.theme.AppThemeContainer
import javax.inject.Inject

internal class MainActivity : BaseActivity() {

    @Inject
    lateinit var navControllerHolder: NavControllerHolder

    @Inject
    lateinit var activityHolder: ActivityHolder

    @Inject
    lateinit var composablesHolder: FeatureComposablesHolder

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<AppComponent>().inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            clear()
            setNew(
                activity = this,
                navController = navController,
            )

            SetComposableContent(
                mainViewModel = mainViewModel,
                viewModelFactory = viewModelFactory,
                composablesHolder = composablesHolder,
                navController = navController,
                themeManager = themeManager,
            )
        }
    }

    private fun clear() {
        activityHolder.activity = null
        navControllerHolder.navController = null
    }

    private fun setNew(
        activity: BaseActivity,
        navController: NavHostController,
    ) {
        activityHolder.activity = activity
        navControllerHolder.navController = navController
    }
}

@Composable
private fun SetComposableContent(
    mainViewModel: MainViewModel,
    viewModelFactory: ViewModelProvider.Factory,
    composablesHolder: FeatureComposablesHolder,
    navController: NavHostController,
    themeManager: ThemeManager,
) {
    val state = mainViewModel.uiState.collectAsStateLifecycleAware()

    AppThemeContainer(
        darkTheme = themeManager.getThemeMode() == AppThemeModeDomain.DARK,
        viewScale = state.value.themeState.viewScale,
    ) {
        Surface {
            BackHandler {
                mainViewModel.onEvent(MainEvent.Ui.OnBackPressed)
            }

            AppNavGraph(
                navController = navController,
                viewModelFactory = viewModelFactory,
                composablesHolder = composablesHolder,
            )
        }
    }
}
