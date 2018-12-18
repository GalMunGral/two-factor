package edu.gatech.wenqi.twofactor

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.Socket

class TestActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val ipFromInput: String = intent.extras.getString("ip_address")

        doAsync {
            val socket = Socket(ipFromInput, 3000)
            val inStream = socket.getInputStream()
            val buffer: ByteArray = ByteArray(1024)
            inStream.read(buffer, 0, 1024)
            val s: String = String(buffer)
            Log.i("doAsync", s)

            uiThread {
                Log.i("uiThread", "HERE")
                textTestInput.text = getString(R.string.test_response, ipFromInput, s)
            }
        }


        buttonSend.setOnClickListener {
//            val intent = Intent(Intent.ACTION_SEND).apply {
//                type = "text/plain"
//                putExtra(Intent.EXTRA_TEXT, ipFromInput)
//            }
//            startActivity(intent)
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent = PendingIntent.getActivity(this, 0, intent,0)

            var mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("TITLE")
                .setContentText("TEST")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(this)) {
                notify(123, mBuilder.build())
            }



        }
    }

}