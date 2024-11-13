package com.example.aheena.di.component_builder.extension

import com.example.aheena.di.AppComponent
import com.example.core_api.di.component.CompositeKey
import com.example.core_api.di.component.DaggerComponent
import com.example.core_api.di.component.FactoryProvider
import com.example.core_api.di.component.createFactoryKey
import com.example.core_api.di.component.getComponentByCompositeKey

internal fun getAppComponent(
    factories: Map<String, FactoryProvider>,
    components: MutableMap<String, DaggerComponent>
): AppComponent = getComponentByCompositeKey(
    factories = factories,
    components = components,
    compositeKey = CompositeKey(AppComponent::class.createFactoryKey())
)

internal fun getUserLifecycleComponents(
    factories: Map<String, FactoryProvider>,
    components: MutableMap<String, DaggerComponent>
): LifecycleComponents = LifecycleComponents(
    getAppComponent(factories, components),
)

internal data class LifecycleComponents(val appComponent: AppComponent)
