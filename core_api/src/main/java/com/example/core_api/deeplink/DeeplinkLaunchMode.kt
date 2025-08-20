package com.example.core_api.deeplink

enum class DeeplinkLaunchMode {

    /**
     * Дефолтный режим. Просто открывается экран для диплинка
     */
    DEFAULT,

    /**
     * Требуется авторизация. После авторизации отображается главный экран и открывается экран для диплинка
     */
    NEED_FOR_AUTHENTICATION,
}
