package edu.gatech.wenqi.twofactor.services

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import edu.gatech.wenqi.twofactor.R
import org.jetbrains.anko.defaultSharedPreferences

const val TAG = "TestFirebase"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d(TAG, "Received FCM token: $token")
        token.let {

            // Save the token
            with(defaultSharedPreferences.edit()) {
                putString(getString(R.string.device_token_key), it)
                apply()
                Log.i("TestFirebase", "Save token $it")
            }

            // Let MainActivity know
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("UPDATE_TOKEN").apply {
                putExtra(getString(R.string.device_token_key), it)
            })
        }
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