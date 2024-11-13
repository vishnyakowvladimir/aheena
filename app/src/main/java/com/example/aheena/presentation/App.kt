package com.example.aheena.presentation

import com.example.aheena.BuildConfig
import com.example.aheena.di.AppComponent
import com.example.aheena.di.ApplicationComponentHolder
import com.example.core.application_info.ApplicationInfo
import com.example.core.di.component.ComponentLifecycle
import com.example.core.di.component.DaggerComponent
import com.example.core.presentation.base.BaseApplication
import kotlin.reflect.KClass

internal class App : BaseApplication(), ComponentLifecycle {

    private val appComponent by lazy {
        AppComponent.init(
            app = this,
            applicationInfo = ApplicationInfo(
                applicationId = BuildConfig.APPLICATION_ID,
                buildType = BuildConfig.BUILD_TYPE,
                versionCode = BuildConfig.VERSION_CODE,
                versionName = BuildConfig.VERSION_NAME,
            ),
        )
    }
    private val applicationComponentHolder by lazy { ApplicationComponentHolder(appComponent) }

    override fun getComponent(
        className: KClass<out DaggerComponent>,
        dependencyType: String?,
        instanceKey: String?,
    ): DaggerComponent {
        return applicationComponentHolder.getComponent(className, dependencyType, instanceKey)
    }

    override fun clearComponent(
        className: KClass<out DaggerComponent>,
        dependencyType: String?,
        instanceKey: String?,
    ) {
        applicationComponentHolder.clearComponent(className, dependencyType, instanceKey)
    }
}
