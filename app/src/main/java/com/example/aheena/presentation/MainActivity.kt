package com.example.aheena.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aheena.di.AppComponent
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.aheena.presentation.compose.MainContainer
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.core.di.extension.getComponent
import com.example.core.presentation.base.BaseActivity
import com.example.core.presentation.theme_manager.ThemeManager
import com.example.core_impl.holder.ActivityHolder
import com.example.core_impl.holder.NavControllerHolder
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
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var themeManager: ThemeManager

    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<AppComponent>().inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            clear()
            setNew(
                activity = this,
                navController = navController
            )

            AppThemeContainer(viewScale = themeManager.getScale()) {
                Surface {
                    MainContainer(
                        viewModelFactory = viewModelFactory,
                        navController = navController,
                        composablesHolder = composablesHolder,
                        mainViewModel = mainViewModel,
                    )
                }
            }
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
