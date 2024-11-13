package com.example.core_api.di.component

interface FactoryProvider {

    fun getFactory(dependencyType: String?): DaggerComponent.Factory
}

@Throws
fun throwFactoryNotFound(dependencyType: String?): Nothing {
    throw Exception("не найдена фабрика для dependencyType = $dependencyType")
}
