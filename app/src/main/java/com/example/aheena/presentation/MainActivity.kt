package com.example.aheena.presentation

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aheena.di.AppComponent
import com.example.aheena.navigation.AppNavGraph
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.core.di.extension.getComponent
import com.example.core.holder.ActivityHolder
import com.example.core.navigation.router.NavControllerHolder
import com.example.core.presentation.base.BaseActivity
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.lib_ui.theme.AppThemeContainer
import javax.inject.Inject

internal class MainActivity : BaseActivity() {

    @Inject
    lateinit var navControllerHolder: NavControllerHolder

    @Inject
    lateinit var activityHolder: ActivityHolder

    @Inject
    lateinit var composablesHolder: FeatureComposablesHolder

//    @Inject
//    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<AppComponent>().inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            clear()

            val navController = rememberNavController()

            setNew(
                activity = this,
                navController = navController,
            )

            SetComposableContent(
                mainViewModel = mainViewModel,
                viewModelFactory = viewModelFactory,
                composablesHolder = composablesHolder,
                navController = navController,
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }

    private fun clear() {
        activityHolder.activity = null
        navControllerHolder.navHostController = null
    }

    private fun setNew(
        activity: BaseActivity,
        navController: NavHostController,
    ) {
        activityHolder.activity = activity
        navControllerHolder.navHostController = navController
    }
}

@Composable
private fun SetComposableContent(
    mainViewModel: MainViewModel,
    viewModelFactory: ViewModelProvider.Factory,
    composablesHolder: FeatureComposablesHolder,
    navController: NavHostController,
) {
    val state = mainViewModel.uiState.collectAsStateLifecycleAware()

    AppThemeContainer(
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

//@Composable
//private fun SetComposableContent(
//    mainViewModel: MainViewModel,
//    viewModelFactory: ViewModelProvider.Factory,
//    composablesHolder: FeatureComposablesHolder,
//    navController: NavHostController,
//    themeManager: ThemeManager,
//) {
//    val state = mainViewModel.uiState.collectAsStateLifecycleAware()
//
//    AppThemeContainer(
//        viewScale = state.value.themeState.viewScale,
//    ) {
//        Surface {
//            BackHandler {
//                mainViewModel.onEvent(MainEvent.Ui.OnBackPressed)
//            }
//
//            val isDarkTheme = isSystemInDarkTheme()
//
//            Column(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                Text(
//                    text = "Change theme",
//                    style = AppTheme.typography.title1Bold,
//                    color = AppTheme.palette.text.primary,
//                    modifier = Modifier.clickable {
//                        if (isDarkTheme) {
//                            themeManager.applyThemeModeLight()
//                        } else {
//                            themeManager.applyThemeModeDark()
//                        }
//                    }
//                )
//                AppNavGraph(
//                    navController = navController,
//                    viewModelFactory = viewModelFactory,
//                    composablesHolder = composablesHolder,
//                )
//            }
//        }
//    }
//}

