package com.example.aheena.di

import com.example.aheena.di.component_builder.factory.ApplicationFactory
import com.example.aheena.di.component_builder.factory.AuthenticationFactory
import com.example.aheena.di.component_builder.factory.MainFactory
import com.example.core.di.component.ComponentLifecycle
import com.example.core.di.component.CompositeKey
import com.example.core.di.component.DaggerComponent
import com.example.core.di.component.FactoryProvider
import com.example.core.di.component.clearComponentByKey
import com.example.core.di.component.createFactoryKey
import com.example.feature_authentication.di.AuthenticationComponent
import com.example.feature_main.di.MainComponent
import kotlin.reflect.KClass

internal class ApplicationComponentHolder(appComponent: AppComponent) : ComponentLifecycle {

    private val components = HashMap<String, DaggerComponent>()

    private val factories: Map<String, FactoryProvider> by lazy {
        mapOf(
            AppComponent::class.createFactoryKey() to ApplicationFactory(appComponent),
            AuthenticationComponent::class.createFactoryKey() to AuthenticationFactory(),
            MainComponent::class.createFactoryKey() to MainFactory(),
        )
    }

    override fun getComponent(
        className: KClass<out DaggerComponent>,
        dependencyType: String?,
        instanceKey: String?
    ): DaggerComponent {
        val compositeKey = CompositeKey(className.createFactoryKey(), dependencyType, instanceKey)
        val daggerComponent = components[compositeKey.toString()]
        if (daggerComponent != null) {
            return daggerComponent
        }

        return factories[compositeKey.factoryKey]
            ?.getFactory(compositeKey.dependencyKey)
            ?.getComponent(factories, components)
            ?.also { components[compositeKey.key] = it }
            ?: throw IllegalStateException("Не найдена фабрика по ключу $compositeKey.")
    }

    override fun clearComponent(className: KClass<out DaggerComponent>, dependencyType: String?, instanceKey: String?) {
        val compositeKey = CompositeKey(className.createFactoryKey(), dependencyType, instanceKey)
        clearComponentByKey(compositeKey, components)
    }
}
