package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.workmanager.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.asuprojects.kotlincustomcomponents.fragments.notifications.NotificationsUtil
import kotlinx.coroutines.*

class CoroutineDownloadWorker(val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override val coroutineContext = Dispatchers.IO

    override suspend fun doWork(): Result = coroutineScope {
        val jobs = (0 until 5).map {
            async {
                delay(2000)
                downloadSynchronously(context, "Download Image completed!")
            }
        }

        // awaitAll will throw an exception if a download fails, which CoroutineWorker will treat as a failure
        jobs.awaitAll()
        Result.success()
    }

    private fun downloadSynchronously(context: Context, msg: String) {
        NotificationsUtil.normalNotification(context, "CoroutineDownloadWorker", msg)
    }
}