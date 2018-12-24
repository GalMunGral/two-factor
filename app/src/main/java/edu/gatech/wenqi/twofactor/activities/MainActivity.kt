package edu.gatech.wenqi.twofactor.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import edu.gatech.wenqi.twofactor.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        buttonTest.setOnClickListener {
            Log.i("test", "Hello")
            val input: String = inputTest.text.toString()
            val intent = Intent(this, TestActivity::class.java).apply {
                putExtra("ip_address", input)
            }
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.default_notification_channel_id),
                "name", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "description"
                importance = NotificationManager.IMPORTANCE_HIGH
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
