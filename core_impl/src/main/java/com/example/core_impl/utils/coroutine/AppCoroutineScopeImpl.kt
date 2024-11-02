package com.example.core_impl.utils.coroutine

import com.example.core.utils.coroutines.AppCoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AppCoroutineScopeImpl @Inject constructor() : AppCoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob()
}
