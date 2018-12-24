package edu.gatech.wenqi.twofactor.activities

import android.annotation.TargetApi
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import edu.gatech.wenqi.twofactor.R
import edu.gatech.wenqi.twofactor.services.TestJobService
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.Socket

class TestActivity: AppCompatActivity() {

    val TEST_JOB_ID = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val ipFromInput: String? = intent?.extras?.getString("ip_address")

        doAsync {
            val socket = Socket(ipFromInput, 3000)
            val inStream = socket.getInputStream()
            val buffer: ByteArray = ByteArray(1024)
            inStream.read(buffer, 0, 1024)
            val s: String = String(buffer)
            Log.i("doAsync", s)

            uiThread {
                Log.i("uiThread", "HERE")
                textTestInput.text = getString(R.string.test_response, ipFromInput, s) ?: ""
            }
        }

        buttonSend.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent = PendingIntent.getActivity(this, 0, intent,0)

            val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val scheduler: JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val result = scheduler.schedule(createJobInfo())
            if (result == JobScheduler.RESULT_SUCCESS) Log.i("Test", "Job scheduled successfully")
        }

    }

    @TargetApi(21)
    private fun createJobInfo() : JobInfo {
        val component = ComponentName(this, TestJobService::class.java)
        val jobInfoBuilder = JobInfo.Builder(TEST_JOB_ID, component).apply {
            setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            setPersisted(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setPeriodic(5000, 2000)
            } else {
                setPeriodic(5000)
            }
        }
        return jobInfoBuilder.build()
    }

}