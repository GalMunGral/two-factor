package edu.gatech.wenqi.twofactor.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.i("TestFirebase", p0)
    }
}