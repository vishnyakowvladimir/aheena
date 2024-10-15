package com.example.aheena.di

import com.example.aheena.di.component_builder.factory.ApplicationFactory
import com.example.core.di.ComponentLifecycle
import com.example.core.di.CompositeKey
import com.example.core.di.DaggerComponent
import com.example.core.di.FactoryProvider
import com.example.core.di.clearComponentByKey
import com.example.core.di.createFactoryKey
import kotlin.reflect.KClass

internal class ApplicationComponentHolder(appComponent: AppComponent) : ComponentLifecycle {

    private val components = HashMap<String, DaggerComponent>()

    private val factories: Map<String, FactoryProvider> by lazy {
        mapOf(
            AppComponent::class.createFactoryKey() to ApplicationFactory(appComponent),
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
