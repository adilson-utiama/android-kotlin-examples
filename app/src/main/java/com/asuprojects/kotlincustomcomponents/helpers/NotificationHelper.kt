package com.asuprojects.kotlincustomcomponents.helpers

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.notifications.NotificationAfterActivity
import com.asuprojects.kotlincustomcomponents.fragments.notifications.model.NotificacaoMensagem
import com.asuprojects.kotlincustomcomponents.helpers.AppConstants.Companion.NOTIFICATION_EXTRA_UUID
import java.util.*

class NotificationHelper {

    companion object {

        private val random = Random()
        private const val CHANNEL_ID = "com.asuprojects.kotlincustomcomponents"
        // Use constant ID for notification used as group summary
        private const val SUMMARY_ID = 0
        private const val GROUP_KEY_MOVS = "com.asuprojects.kotlincustomcomponents.GROUPED"

        /**
         *  Simple Notification
         *  Dispara uma notificação com titulo e mensagem curta e breve
         *  - Id de notificacao randomizado
         *  - Cada notificação é unica
         */
        fun notifySimpleContentRandomId(context: Context, title: String, content: String){

            val notifyId: Int = randomNotificationId()

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setColor(Color.BLUE)
                .setAutoCancel(true)

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "NotificationId"
                val channelName = "NotificationChannel"

                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)

                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.setShowBadge(true)
                channel.enableVibration(true)

                builder.setChannelId(channelId)
                mNotificationManager?.createNotificationChannel(channel)

            }else{
                builder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            mNotificationManager?.notify(notifyId, builder.build())

        }

        /**
         * Notificação Expansivel
         * Dispara uma notificação com titulo e mensagem com conteudo longo, podendo ser expandido ao clicar no icone
         * - Id de notificacao randomizado
         * - Mensagem expansivel
         */
        fun notifyLargeContentRandomId(context: Context, title: String, content: String) {
            val notifyId: Int = randomNotificationId()

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setColor(Color.BLUE)
                .setStyle(NotificationCompat.BigTextStyle().bigText(content))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "NotificationId"
                val channelName = "NotificationChannel"

                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)

                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.setShowBadge(true)
                channel.enableVibration(true)

                builder.setChannelId(channelId)
                mNotificationManager?.createNotificationChannel(channel)

            }else{
                builder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            mNotificationManager?.notify(notifyId, builder.build())
        }

        fun notifyLargeContentIntentRandomId(context: Context, title: String, content: String, uuid: String) {

            /** Create an explicit intent for an activity in your app **/
            val intent = Intent(context, NotificationAfterActivity::class.java).apply {
                //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(NOTIFICATION_EXTRA_UUID, uuid)
            }

            //val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val notifyId: Int = randomNotificationId()

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setColor(Color.BLUE)
                .setStyle(NotificationCompat.BigTextStyle().bigText(content))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the Intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "NotificationId"
                val channelName = "NotificationChannel"

                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)

                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.setShowBadge(true)
                channel.enableVibration(true)

                builder.setChannelId(channelId)
                mNotificationManager?.createNotificationChannel(channel)

            }else{
                builder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            mNotificationManager?.notify(notifyId, builder.build())
        }

        fun notifyGroupedContent(context: Context, mensagens: List<NotificacaoMensagem>) {
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Notificações Agrupadas")
                .setContentText("${mensagens.size} Mensagens")
                .setSmallIcon(R.drawable.ic_app_icon)
                // Build summary info into InbosStyle template
                .setStyle(NotificationCompat.InboxStyle()
                    .addLine("Notificação 1")
                    .addLine("Notificação 2")
                    .setBigContentTitle("${mensagens.size} Mensagens")
                    .setSummaryText("Notificações Agrupadas"))
                // specify whic group this notification belongs to
                .setGroup(GROUP_KEY_MOVS)
                // Set this notification as the summary for the group
                .setGroupSummary(true)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "NotificationGroupId"
                val channelName = "NotificationChannelGroup"

                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.setShowBadge(true)
                channel.enableVibration(true)

                summaryNotification.setChannelId(channelId)
                mNotificationManager?.createNotificationChannel(channel)

            }else{
                summaryNotification.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            var nId = 1000

            NotificationManagerCompat.from(context).apply {
                mensagens.forEach {
                    notify(nId++, generateNotificationForGroup(context, it))
                }
                notify(SUMMARY_ID, summaryNotification.build())
            }
        }
        fun generateNotificationForGroup(context: Context, notificacaoMensagem: NotificacaoMensagem): Notification {
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            /** Create an explicit intent for an activity in your app **/
            val intent = Intent(context, NotificationAfterActivity::class.java).apply {
                //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(NOTIFICATION_EXTRA_UUID, notificacaoMensagem.movUuid)
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val builder =  NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle(notificacaoMensagem.titulo)
                .setContentText(notificacaoMensagem.mensagem)
                .setColor(Color.BLUE)
                .setGroup(GROUP_KEY_MOVS)

            if(notificacaoMensagem.movUuid.isNotEmpty()){
                // Set the Intent that will fire when the user taps the notification
                builder.setContentIntent(pendingIntent)
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "NotificationGroupId"
                val channelName = "NotificationChannelGroup"

                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.setShowBadge(true)
                channel.enableVibration(true)

                builder.setChannelId(channelId)
                mNotificationManager?.createNotificationChannel(channel)

            }else{
                builder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            return builder.build()
        }


        fun notifyGroupedContentTest(context: Context) {
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            val notification1 = generateNotificationForGroupTest(context, 1)
            val notification2 = generateNotificationForGroupTest(context, 2)
            val notification3 = generateNotificationForGroupTest(context, 3)
            val notification4 = generateNotificationForGroupTest(context, 4)

            val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Notificações Agrupadas")
                .setContentText("4 Mensagens")
                .setSmallIcon(R.drawable.ic_app_icon)
                // Build summary info into InbosStyle template
                .setStyle(NotificationCompat.InboxStyle()
                    .addLine("Notificação 1")
                    .addLine("Notificação 2")
                    .setBigContentTitle("4 Mensagens")
                    .setSummaryText("Notificações Agrupadas"))
                // specify whic group this notification belongs to
                .setGroup(GROUP_KEY_MOVS)
                // Set this notification as the summary for the group
                .setGroupSummary(true)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "NotificationGroupId"
                val channelName = "NotificationChannelGroup"

                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.setShowBadge(true)
                channel.enableVibration(true)

                summaryNotification.setChannelId(channelId)
                mNotificationManager?.createNotificationChannel(channel)

            }else{
                summaryNotification.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            NotificationManagerCompat.from(context).apply {
                notify(101, notification1)
                notify(102, notification2)
                notify(103, notification3)
                notify(104, notification4)
                notify(SUMMARY_ID, summaryNotification.build())
            }
        }

        private fun randomNotificationId(bound: Int = 100): Int {
            return random.nextInt(bound)
        }

        private fun generateNotificationForGroupTest(context: Context, number: Int): Notification {
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            val builder =  NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle("Notificação $number")
                .setContentText("Conteudo da Notificação $number")
                .setColor(Color.BLUE)
                .setGroup(GROUP_KEY_MOVS)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "NotificationGroupId"
                val channelName = "NotificationChannelGroup"

                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.setShowBadge(true)
                channel.enableVibration(true)

                builder.setChannelId(channelId)
                mNotificationManager?.createNotificationChannel(channel)

            }else{
                builder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            return builder.build()
        }
    }
}