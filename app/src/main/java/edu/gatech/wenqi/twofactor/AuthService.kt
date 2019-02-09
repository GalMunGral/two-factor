package edu.gatech.wenqi.twofactor

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class AuthService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        with(NotificationManagerCompat.from(this)) {
            cancel(1) // Dismiss notification (in cases there is one)
        }
        val sessionId = intent?.getStringExtra("session_id")
        Volley.newRequestQueue(this).add(JsonObjectRequest(
            Request.Method.POST,
            "http://${getString(R.string.server_ip)}:3000/android/confirm",
            JSONObject().apply {
                put("sessionId", sessionId)
            },
            Response.Listener {
                Log.i("TestFirebase", "Success!")
            },
            Response.ErrorListener {
                Log.d("TestFirebase", it.toString())
            }
        ))
        return Service.START_STICKY

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}