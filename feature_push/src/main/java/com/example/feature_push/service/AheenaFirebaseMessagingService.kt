package com.example.feature_push.service

import android.util.Log
import com.example.core_api.log.LOG_TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AheenaFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(LOG_TAG, "New FCM Token: $token")
        /* Отправка токена на бэк */
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(LOG_TAG, "message: $message")
    }
}
