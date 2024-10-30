package com.example.workmanager.logworker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SecondLogWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("com.example.workmanager.logworker.SecondLogWorker", "com.example.workmanager.logworker.SecondLogWorker task completed!")
        return Result.success() // Indicate success
    }
}
