package com.example.core_api.di.component

import android.util.Log
import com.example.core_api.di.extension.throwDaggerWrongComponent
import kotlin.reflect.KClass

/**
 * Предоставляет [DaggerComponent].
 *
 * */
interface ComponentLifecycle {

    /**
     * Получить инстанс компонента. Если компонента ещё нет, то создаст новый компонент и сохранит у себя до очистки
     * (см. [clearComponent]).
     *
     * @param className - класс компонента, который должен быть возвращен.
     * @param dependencyType - жизненный цикл компонента [className], когда необходимо сохранить два или более инстанса [className].
     * @param instanceKey - дополнительный идентификатор для сохранения уникального инстанса компонента.
     * @return [DaggerComponent]
     * */
    fun getComponent(
        className: KClass<out DaggerComponent>,
        dependencyType: String? = null,
        instanceKey: String? = null,
    ): DaggerComponent

    /**
     * Очистить компонент.
     * @param className - класс компонента, который должен быть очищен.
     * @param dependencyType - источник зависимостей [className], когда в разных ситуациях необходимо использовать разные источники данных.
     * @param instanceKey - дополнительный идентификатор для сохранения уникального инстанса компонента.
     * */
    fun clearComponent(
        className: KClass<out DaggerComponent>,
        dependencyType: String?,
        instanceKey: String?,
    )
}

/**
 * Интерфейс-метка для dagger компонентов.
 * Используется для более логичного типа вместо [Any] в ситуациях, когда тип придется кастить.
 * */
interface DaggerComponent {

    class Factory private constructor(
        private val componentCreator: (
            factories: Map<String, FactoryProvider>,
            MutableMap<String, DaggerComponent>,
        ) -> DaggerComponent
    ) {

        companion object {

            fun builder(): Builder = Builder()
        }

        class Builder {

            var componentCreator:
                (
                    (factories: Map<String, FactoryProvider>, MutableMap<String, DaggerComponent>)
                -> DaggerComponent
                )? = null

            inline fun <reified T : DaggerComponent> fromSingleDependency(
                compositeKey: CompositeKey? = null,
                crossinline block: (dependencyComponent: T) -> DaggerComponent,
            ) = apply {
                val key = compositeKey ?: T::class.createCompositeKey()

                componentCreator = { factories, components ->
                    val component = getComponentByCompositeKey<T>(
                        compositeKey = key,
                        factories = factories,
                        components = components
                    )
                    block.invoke(component)
                }
            }

            fun fromMultipleDependencies(
                componentCreator: (
                    factories: Map<String, FactoryProvider>,
                    MutableMap<String, DaggerComponent>,
                ) -> DaggerComponent
            ) = apply {
                this.componentCreator = componentCreator
            }

            fun build(): Factory {
                return Factory(
                    componentCreator
                        ?: throw IllegalArgumentException("Не найдено ни одного провайдера.")
                )
            }
        }

        /**
         * Получить компонент по ключу [key]. Если компонент уже существует, то он будет взят из [components],
         * иначе будет обращение к фабрике по [key] и создан новый компонент. Созданный компонент будет сохранён в
         * [components] по ключу [key].
         *
         * @param key - ключ, по которому будет получен компонент.
         * @param factories - карта фабрик, предоставляющих компоненты.
         * @param components - ранее созданные компоненты
         *
         * @return [DaggerComponent]
         * */
        fun getComponent(
            factories: Map<String, FactoryProvider>,
            components: MutableMap<String, DaggerComponent>,
        ): DaggerComponent = componentCreator(factories, components)
    }
}

inline fun <reified T : DaggerComponent> getComponentByCompositeKey(
    compositeKey: CompositeKey? = null,
    factories: Map<String, FactoryProvider>,
    components: MutableMap<String, DaggerComponent>,
): T {
    val ck = compositeKey ?: T::class.createCompositeKey()
    return (
        components[ck.key]
            ?: factories[ck.factoryKey]
                ?.getFactory(ck.dependencyKey)
                ?.getComponent(factories = factories, components = components)
        )
        ?.also {
            components[ck.key] = it
            Log.v("LoggedHashMap", "не найден объект по ключу ${ck.key}, создаем его неявно $it")
        }
        ?.castTo()
        ?: throw IllegalStateException("Не найдена фабрика по ключу $ck.")
}

fun clearComponentByKey(compositeKey: CompositeKey, factories: MutableMap<String, DaggerComponent>) {
    factories.remove(compositeKey.key)
        ?: Log.w("ComponentLifecycle", "Не найдена фабрика по ключу $compositeKey.")
}

inline fun <reified T> DaggerComponent.castTo(): T {
    return if (this is T) {
        this
    } else {
        this.throwDaggerWrongComponent()
    }
}

fun KClass<out DaggerComponent>.createFactoryKey(): String = (qualifiedName ?: "")

fun KClass<out DaggerComponent>.createCompositeKey(
    dependencyKey: String? = null,
    instanceType: String? = null
) = CompositeKey(this.createFactoryKey(), dependencyKey, instanceType)
