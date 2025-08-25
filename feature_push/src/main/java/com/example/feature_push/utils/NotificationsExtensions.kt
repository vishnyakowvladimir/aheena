package com.example.feature_push.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.core_api.log.AppLogger
import com.google.firebase.messaging.FirebaseMessaging

internal const val PUSH_NOTIFICATION_CHANNEL_ID = "PUSH_NOTIFICATION_CHANNEL_ID"
private const val PUSH_NOTIFICATION_CHANNEL_NAME = "Aheena Уведомления"

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
 * Запрашивает разрешение на уведомления
 * */
fun AppCompatActivity.askPostNotificationsPermission(
    launcher: ActivityResultLauncher<String>,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED -> {
                /**
                 * Пользователь уже дал разрешение
                 * */
            }

            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                /**
                 * Пользователь ОТКАЗАЛ хотя бы один раз → нужно объяснить, зачем это разрешение
                 * */
            }

            else -> {
                /**
                 * Первый запрос ИЛИ отказ с "Never ask again" → сразу показываем системный диалог
                 * */
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
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
