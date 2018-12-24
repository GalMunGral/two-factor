package edu.gatech.wenqi.twofactor.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val TAG = "TestFirebase"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d(TAG, "Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        Log.d(TAG, "From: ${message?.from}")

        message?.notification?.let {
            Log.d(TAG, "Notification: ${it.title}, ${it.body}")
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}