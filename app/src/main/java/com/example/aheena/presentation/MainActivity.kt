package com.example.aheena.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aheena.di.AppComponent
import com.example.aheena.navigation.AppNavGraph
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainUiCommand
import com.example.core_api.di.extension.getComponent
import com.example.core_api.holder.ActivityHolder
import com.example.core_api.navigation.router.NavControllerHolder
import com.example.core_api.presentation.base.BaseActivity
import com.example.core_api.utils.extension.collectAsStateLifecycleAware
import com.example.lib_ui.R
import com.example.lib_ui.components.snackbar.AppSnackbarVisuals
import com.example.lib_ui.components.snackbar.AppSwipeableSnackbarHost
import com.example.lib_ui.theme.AppThemeContainer
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

internal class MainActivity : BaseActivity() {

    @Inject
    lateinit var navControllerHolder: NavControllerHolder

    @Inject
    lateinit var activityHolder: ActivityHolder

    @Inject
    lateinit var composablesHolder: FeatureComposablesHolder

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

        checkDeeplink()
    }

    override fun onNewIntent(intent: Intent) {
        checkDeeplink(intent)
        super.onNewIntent(intent)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        mainViewModel.onEvent(MainEvent.Ui.OnTouch)
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }

    private fun checkDeeplink(newIntent: Intent = intent): Boolean {
        val uri = newIntent.data

        if (newIntent.action == Intent.ACTION_VIEW && uri != null) {
            newIntent.data = null
            // тут надо добавить обработку диплинка (пока не разработана)
        }

        return uri != null
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
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                mainViewModel.uiCommand.collectLatest { command ->
                    when (command) {
                        is MainUiCommand.ShowSnackbar -> {
                            snackbarHostState.showSnackbar(
                                AppSnackbarVisuals(
                                    message = command.text,
                                    resourceId = R.drawable.ic_20dp_status_ok_filled,
                                    status = AppSnackbarVisuals.Status.ERROR,
                                ),
                            )
                        }
                    }
                }
            }

            BackHandler {
                mainViewModel.onEvent(MainEvent.Ui.OnBackPressed)
            }

            Scaffold(
                snackbarHost = { AppSwipeableSnackbarHost(snackbarHostState = snackbarHostState) },
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            )
            { paddingValues ->

                Box(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    AppNavGraph(
                        navController = navController,
                        viewModelFactory = viewModelFactory,
                        composablesHolder = composablesHolder,
                    )
                }
            }
        }
    }
}

