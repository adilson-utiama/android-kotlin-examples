package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.work.*
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.workmanager.workers.CoroutineDownloadWorker
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.workmanager.workers.DoSomethingWorker
import com.asuprojects.kotlincustomcomponents.helpers.FabAnimationsHelper
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_work_manager_ex.*
import java.util.*
import java.util.concurrent.TimeUnit

class WorkManagerExActivity : AppCompatActivity() {

    private var isRotate = false

    private var periodicWorkRequestId: UUID? = null

    companion object {
        val WORK_MESSAGE = "WORK_MESSAGE"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager_ex)

        supportActionBar?.apply {
            this.title = "WorkManager"
            this.setDisplayHomeAsUpEnabled(true)
        }

        FabAnimationsHelper.initFabMenuChild(fab_workmanager_download)
        FabAnimationsHelper.initFabMenuChild(fab_workmanager_mic)
        FabAnimationsHelper.initFabMenuChild(fab_workmanager_call)

        fab_workmanager.setOnClickListener {
            isRotate = FabAnimationsHelper.openFabMenu(it, !isRotate)
            if(isRotate){
                FabAnimationsHelper.showInFabMenuChild(fab_workmanager_download)
                FabAnimationsHelper.showInFabMenuChild(fab_workmanager_mic)
                FabAnimationsHelper.showInFabMenuChild(fab_workmanager_call)
            }else{
                FabAnimationsHelper.showOutFabMenuChild(fab_workmanager_download)
                FabAnimationsHelper.showOutFabMenuChild(fab_workmanager_mic)
                FabAnimationsHelper.showOutFabMenuChild(fab_workmanager_call)
            }
        }

        fab_workmanager_download.setOnClickListener {
            closeFabMenu()

            val workMessage = "OneTimeWorkRequest DownloadWorker Request"

            val messageData = workDataOf(WORK_MESSAGE to workMessage)

            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                //.setRequiresCharging(true)
                .build()

            val doSomethingWorker =
                OneTimeWorkRequestBuilder<CoroutineDownloadWorker>()
                    .setConstraints(constraints)
                    .setInputData(messageData)
                    .addTag("OneTimeWorkRequestDownloadWorker")
                    .build()
            WorkManager.getInstance(this@WorkManagerExActivity).enqueue(doSomethingWorker)

            ToastUtil.msg(this@WorkManagerExActivity, "OneTimeWorkRequest DownloadWorker clicked!")
        }

        fab_workmanager_mic.setOnClickListener {

            closeFabMenu()

            val workMessage = "OneTimeWorkRequestBuilder Request"

            val messageData = workDataOf(WORK_MESSAGE to workMessage)

            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                //.setRequiresCharging(true)
                .build()

            val doSomethingWorker =
                OneTimeWorkRequestBuilder<DoSomethingWorker>()
                    .setConstraints(constraints)
                    .setInputData(messageData)
                    .addTag("OneTimeWorkRequestBuilderRequest")
                    .build()
            WorkManager.getInstance(this@WorkManagerExActivity).enqueue(doSomethingWorker)

            ToastUtil.msg(this@WorkManagerExActivity, "OneTimeWorkRequestBuilder clicked!")
        }

        fab_workmanager_call.setOnClickListener {

            closeFabMenu()

            val workMessage = "PeriodicWorkRequestBuilderRequest Request"

            val messageData = workDataOf(WORK_MESSAGE to workMessage)

            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                //.setRequiresCharging(true)
                .build()

            //Periodic work has a minimum interval of 15 minutes.

            val doSomethingWorker =
                PeriodicWorkRequestBuilder<DoSomethingWorker>(16, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .setInputData(messageData)
                    .addTag("PeriodicWorkRequestBuilderRequest")
                    .build()

            periodicWorkRequestId = doSomethingWorker.id

            WorkManager.getInstance(this@WorkManagerExActivity).enqueue(doSomethingWorker)

            ToastUtil.msg(this@WorkManagerExActivity, "PeriodicWorkRequestBuilder clicked!")
        }

        btn_cancel_work.setOnClickListener {
            periodicWorkRequestId?.apply {
                if(!WorkManager.getInstance(this@WorkManagerExActivity).getWorkInfoById(this).isCancelled){
                    WorkManager.getInstance(this@WorkManagerExActivity).cancelWorkById(this)
                }else{
                    Toast.makeText(this@WorkManagerExActivity, "Work ja se encontra cancelado!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun closeFabMenu(){
        isRotate = FabAnimationsHelper.closeFabMenu(fab_workmanager, !isRotate)
        FabAnimationsHelper.showOutFabMenuChild(fab_workmanager_download)
        FabAnimationsHelper.showOutFabMenuChild(fab_workmanager_mic)
        FabAnimationsHelper.showOutFabMenuChild(fab_workmanager_call)
    }

    override fun onDestroy() {
        periodicWorkRequestId?.apply {
            if(!WorkManager.getInstance(this@WorkManagerExActivity).getWorkInfoById(this).isCancelled){
                WorkManager.getInstance(this@WorkManagerExActivity).cancelWorkById(this)
            }else{

            }
        }
        super.onDestroy()
    }
}
