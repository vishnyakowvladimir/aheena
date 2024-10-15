package com.example.core.di.extension

import android.app.Activity
import android.app.Application
import com.example.core.di.ComponentLifecycle
import com.example.core.di.DaggerComponent

@Throws
inline fun <reified T : DaggerComponent> T.throwDaggerWrongComponent(): Nothing {
    throw RuntimeException("получен некорректный инстанс компонента. Ожидалось ${T::class}, получен $this")
}

inline fun <reified T : DaggerComponent> Application.getComponent(
    dependencyType: String? = null,
    instanceKey: String? = null
): T {
    return (this as ComponentLifecycle)
        .getComponent(T::class, dependencyType, instanceKey)
        .let {
            if (it is T) it else it.throwDaggerWrongComponent()
        }
}


inline fun <reified T : DaggerComponent> Activity.getComponent(
    dependencyType: String? = null,
    instanceKey: String? = null
): T {
    return application.getComponent(dependencyType, instanceKey)
}

inline fun <reified T : DaggerComponent> Application.clearComponent(
    dependencyType: String? = null,
    instanceKey: String? = null
) {
    (this as ComponentLifecycle)
        .clearComponent(T::class, dependencyType, instanceKey)
}


inline fun <reified T : DaggerComponent> Activity.clearComponent(
    dependencyType: String? = null,
    instanceKey: String? = null,
) {
    application.clearComponent<T>(dependencyType, instanceKey)
}


