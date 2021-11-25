package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.alarmmanager.bootreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log


class BootNotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (Intent.ACTION_BOOT_COMPLETED == intent!!.action) {
            val goToService = Intent(context, BootNotificationService::class.java)
            // Valida a versao do Android. A partir do 8, usar startForegroundService
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context?.startForegroundService(goToService)
                } else {
                    context?.startService(goToService)
                }
            } catch (e: Exception) {
                Log.e("EXCEPTION", "onReceive: Error", e)
            }
        }

    }
}