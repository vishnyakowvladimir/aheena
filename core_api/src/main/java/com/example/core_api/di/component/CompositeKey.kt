package com.example.core_api.di.component

class CompositeKey(
    val factoryKey: String,
    val dependencyKey: String? = null,
    val instanceKey: String? = null,
) {
    val key = "$factoryKey${dependencyKey ?: ""}${instanceKey ?: ""}"

    override fun toString(): String {
        return key
    }
}
