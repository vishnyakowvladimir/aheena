package com.example.core.di.extension

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.core.di.component.ComponentLifecycle
import com.example.core.di.component.DaggerComponent
import com.example.core.presentation.base.BaseActivity

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


inline fun <reified T : DaggerComponent> BaseActivity.getComponent(
    dependencyType: String? = null,
    instanceKey: String? = null
): T {
    return application.getComponent(dependencyType, instanceKey)
}

inline fun <reified T : DaggerComponent> Context.getComponent(
    dependencyType: String? = null,
    instanceKey: String? = null
): T {
    return (this as BaseActivity).getComponent(dependencyType, instanceKey)
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

inline fun <reified T : DaggerComponent> Context.clearComponent(
    dependencyType: String? = null,
    instanceKey: String? = null,
) {
    (this as BaseActivity).clearComponent<T>(dependencyType, instanceKey)
}


