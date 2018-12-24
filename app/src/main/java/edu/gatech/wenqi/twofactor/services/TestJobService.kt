package edu.gatech.wenqi.twofactor.services

import android.annotation.TargetApi
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

@TargetApi(21)

class TestJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.i("NewTest", "Job started")
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.i("NewTest", "Job Stopped")
        return true
    }
}