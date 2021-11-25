package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.workmanager.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.asuprojects.kotlincustomcomponents.fragments.notifications.NotificationsUtil
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.workmanager.WorkManagerExActivity.Companion.WORK_MESSAGE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DoSomethingWorker(private val appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val message = inputData.getString(WORK_MESSAGE).toString()

        val result = doSomething(message)

        val successReturn = "Executado com Sucesso!"

        // Create the output of the work
        val outputData = workDataOf("KEY_IMAGE_URL" to successReturn)

        return if (result) {
            Result.success(outputData)
        } else {
            Result.failure()
        }
    }

    private fun doSomething(message: String): Boolean {
        GlobalScope.launch {
            Log.i("TESTE", "Fazendo algo....")
            delay(2000)
            Log.i("TESTE", "$message executado com Sucesso!")

            NotificationsUtil.normalNotification(appContext, "Background Task", "$message executado com Sucesso!")
        }
        return true
    }

}