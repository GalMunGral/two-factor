package edu.gatech.wenqi.twofactor.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import edu.gatech.wenqi.twofactor.R
import org.json.JSONObject

class AuthService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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