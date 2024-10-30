package com.example.workmanager



import com.example.workmanager.logworker.LogWorker
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.workmanager.logworker.SecondLogWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStartWork = findViewById<Button>(R.id.button_start_work)
        buttonStartWork.setOnClickListener { startWork() }
    }

    private fun startWork() {
        // Define constraints: Run only when connected to Wi-Fi
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Only run when connected to the internet
            .build()

        // Create the work requests
        val logWorkRequest = OneTimeWorkRequestBuilder<LogWorker>()
            .setConstraints(constraints) // Apply constraints
            .build()

        val secondLogWorkRequest = OneTimeWorkRequestBuilder<SecondLogWorker>()
            .setConstraints(constraints) // Apply constraints
            .build()

        // Chain the work requests
        WorkManager.getInstance(this)
            .beginWith(logWorkRequest) // First task
            .then(secondLogWorkRequest) // Second task
            .enqueue()

        // Set up a periodic task to log a message every 15 minutes
        val periodicLogWorkRequest = PeriodicWorkRequestBuilder<LogWorker>(5, TimeUnit.MINUTES)
            .setConstraints(constraints) // Apply constraints
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "PeriodicLogWork",
            ExistingPeriodicWorkPolicy.UPDATE, // Replace if already exists
            periodicLogWorkRequest
        )
    }
}
