package com.cs4520.assignment5.view.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.assignment5.workers.ProductListUpdateWorker
import java.util.concurrent.TimeUnit



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runProductListUpdateWorker()
        setContent {
            MainScreen()
        }
    }

    private fun runProductListUpdateWorker() {
        val req = PeriodicWorkRequestBuilder<ProductListUpdateWorker>(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "UpdateProductListEveryHour",
            ExistingPeriodicWorkPolicy.UPDATE,
            req
        )
    }
}