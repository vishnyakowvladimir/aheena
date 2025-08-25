package com.example.feature_push.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.feature_push.service.PUSH_NOTIFICATION_CHANNEL_ID
import com.example.feature_push.service.PUSH_NOTIFICATION_CHANNEL_NAME

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
