package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.alarmmanager.AlarmReceiver.Companion.EXTRA_ALARM_MSG_CONTENT
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.alarmmanager.bootreceiver.BootNotificationReceiver
import java.util.*


class AlarmHelper {

    companion object {

        fun setarAlarmParaNotificacao(context: Context, msg: String, alarmTime: Long){
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(EXTRA_ALARM_MSG_CONTENT, msg)

            //Intent para ser executado quando o alrme disparar
            val pendingIntent = PendingIntent.getBroadcast(context,
                generateRandomRequestCode(), intent, PendingIntent.FLAG_ONE_SHOT)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

            // Enable a BootNotificationReceiver
            // Necessario para reagendar alarmes setados em outros dias
            // Ao reiniciar o dispositivo, TODOS os alarmes setados são apagados do Android por Padrao
            // Se utilizando do BootNotificationReceiver para executar o BootNotificationService
            // para recuperar alarmes setados gravados em algum banco de dados, por exemplo
            val receiver = ComponentName(context, BootNotificationReceiver::class.java)
            val pm = context.packageManager
            pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )

            alarmManager?.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
                } else {
                    AlarmManagerCompat.setExact(alarmManager, AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
                }
            }
        }

        private fun generateRandomRequestCode(): Int {
            val random = Random()
            return random.nextInt(1000)
        }
    }


}