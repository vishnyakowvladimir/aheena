package com.example.aheena.presentation

import com.example.aheena.di.AppComponent
import com.example.aheena.di.ApplicationComponentHolder
import com.example.core.di.component.ComponentLifecycle
import com.example.core.di.component.DaggerComponent
import com.example.core.presentation.base.BaseApplication
import kotlin.reflect.KClass

internal class App : BaseApplication(), ComponentLifecycle {

    private val appComponent by lazy { AppComponent.init(this) }
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
