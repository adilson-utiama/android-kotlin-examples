package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.alarmmanager.bootreceiver

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import com.asuprojects.kotlincustomcomponents.fragments.notifications.NotificationsUtil

class BootNotificationService : IntentService("BootNoticationService") {

    private val SERVICE_ID = 200

    override fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(SERVICE_ID, NotificationsUtil.simpleNotification(
                applicationContext,
                "Notification Service Working..."))
        }
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {

        //Aqui podera realizar atividades todas as vezes que inciar o dispositivo

        Log.i("TESTE", "BootNotificationService executando....")

    }

}