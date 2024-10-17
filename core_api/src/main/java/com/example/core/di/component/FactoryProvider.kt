package com.example.core.di.component

import com.example.core.di.component.DaggerComponent

interface FactoryProvider {

    fun getFactory(dependencyType: String?): DaggerComponent.Factory
}

@Throws
fun throwFactoryNotFound(dependencyType: String?): Nothing {
    throw Exception("не найдена фабрика для dependencyType = $dependencyType")
}
