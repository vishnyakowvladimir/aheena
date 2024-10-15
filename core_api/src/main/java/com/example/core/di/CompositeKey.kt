package com.example.core.di

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
