package com.example.feature_push.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.core_api.log.AppLogger
import com.example.feature_push.service.PUSH_NOTIFICATION_CHANNEL_ID
import com.example.feature_push.service.PUSH_NOTIFICATION_CHANNEL_NAME
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Создание Channel для пуш-уведомлений
 * */
fun Context.createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            PUSH_NOTIFICATION_CHANNEL_ID,
            PUSH_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}

/**
 * Подготовка FCM.
 * Получение токена устройства и отправка токена на бэк.
 * */
fun prepareFirebaseMessaging() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            AppLogger.log("Fetching FCM registration token failed: ${task.exception?.message}")
            return@addOnCompleteListener
        }

        /**
         * Отправка токена на бэк
         * */
        val token = task.result
        AppLogger.log("Init FCM Token: $token")
    }
}
