package com.asuprojects.kotlincustomcomponents.fragments.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.asuprojects.kotlincustomcomponents.R

class NotificationsUtil {

    companion object {

        private val CHANNEL_ID = "com.asuprojects.kotlincustomcompponents"

        /*
        * A notificação de um dispositivo Android Oreo em execução ou superior é diferente de nougat ou inferior
        * */

        fun normalNotification(context: Context, title: String, message: String){

            val notifyId: Int = 1000

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(Color.BLUE)
                .setAutoCancel(true)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val channelId1000 = "1000"
                val channelName1000 = "Channel1000"


                val channel = NotificationChannel(
                    channelId1000,
                    channelName1000,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.importance = NotificationManager.IMPORTANCE_HIGH
                channel.enableVibration(true)
                channel.enableLights(true)
                channel.setShowBadge(true)
                mBuilder.setChannelId(channelId1000)
                mNotificationManager?.createNotificationChannel(channel)
            }else{
                mBuilder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            mNotificationManager?.notify(notifyId, mBuilder.build())
        }

        fun simpleNotification(context: Context, title: String): Notification {

            val channelServiceId = "com.susprojects.KOTLIN_CHANNEL_ID"
            val channelService = "com.asusprojects.KOTLIN_SERVICE_CHANNEL_NAME"

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_SYSTEM)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channel = NotificationChannel(
                    channelServiceId,
                    channelService,
                    NotificationManager.IMPORTANCE_NONE
                )
                channel.importance = NotificationManager.IMPORTANCE_NONE
                channel.enableVibration(false)
                channel.enableLights(false)
                mBuilder.setChannelId(channelServiceId)
                mNotificationManager?.createNotificationChannel(channel)
            }else{
                mBuilder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            return mBuilder.build()
        }

        /********* Simple Notification ***********/
        fun defaultNotification(context: Context, title: String, content: String){

            /* Id para a Notificação */
            val notifyId: Int = 100

            /** Obtem uma instancia do NotificationManager **/
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(content)
                .setColor(Color.BLUE)
                .setAutoCancel(true)


            /** Obtém uma instância do NotificationManager Service **/
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
               /** Para Android Oreo ou Acima **/

                /** Precisamos criar um Canal para o Android O para exibir notificações **/
                val channelId1 = "1"
                val channelName1 = "Channel1"

                /** Inicializa NotificationChannel **/
                val channel = NotificationChannel(channelId1, channelName1, NotificationManager.IMPORTANCE_HIGH)

                /** Ativa Luz de Notificação, Vibração e mostra badge para true **/
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.setShowBadge(true)
                channel.enableVibration(true)

                /** Seta o ChannelID para o Notification.Builder **/
                builder.setChannelId(channelId1)

                /** Cria um Channel caso nulo **/
                mNotificationManager?.createNotificationChannel(channel)


            }else{
                /** Para versoes de Android inferiores ao Oreo **/

                /** Uso da declaração abaixo para tom de notificação, luzes de notificação, vibração **/
                builder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            /** Use getIntent se desejar Abrir a Activity Atual
             * ou use
             * val intent = Intent(context, MyClass::class.java)
             * Use seu nome de classe em vez de MyClass
             */
            val intent = Intent(context, NotificationAfterActivity::class.java)

            /** Cria uma taskStackBuilder **/
            val stackBuilder = TaskStackBuilder.create(context)

            /** Adiciona NextIntent **/
            stackBuilder.addNextIntent(intent)
            val pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT)

            /** Defini um Conteudo de uma Intent para abrir ao clicar na Notificação **/
            builder.setContentIntent(pendingIntent)

            /*** Ao emitir várias notificações sobre o mesmo tipo de evento,
             * é uma prática recomendada para seu aplicativo tentar atualizar uma notificação existente
             * com essas novas informações, em vez de criar imediatamente uma nova notificação.
             * Se você deseja atualizar esta notificação posteriormente, é necessário atribuir um ID a ela.
             * Você pode usar esse ID sempre que emitir uma notificação subsequente.
             * Se a notificação anterior ainda estiver visível, o sistema atualizará essa notificação existente,
             * em vez de criar um novo. Neste exemplo, o ID da notificação é 100 ***/

            mNotificationManager?.notify(notifyId, builder.build())

        }

        /********* Inbox Style Motification ***********/
        fun inboxStyleNotification(context: Context, title: String, content: String) {
            val notifyId = 200

            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(content)
                .setColor(Color.BLUE)
                .setAutoCancel(true)

            /** Seta o style da Notificação como Inbox **/
            val inboxStyle = NotificationCompat.InboxStyle()

            /** Seta o titulo quando expandir a Notificação **/
            inboxStyle.setBigContentTitle(title)

            /** Adicione aqui todas as suas mensagens ou use Loop para gerar mensagens **/
            inboxStyle.addLine("Mensagem 1")
            inboxStyle.addLine("Mensagem 2")
            inboxStyle.addLine("Mensagem 3")
            inboxStyle.addLine("Mensagem 4")
            inboxStyle.addLine("Mensagem 5")

            mBuilder.setStyle(inboxStyle)

            val intent = Intent(context, NotificationAfterActivity::class.java)
            val stackBuilder = TaskStackBuilder.create(context)

            stackBuilder.addNextIntent(intent)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            mBuilder.setContentIntent(pendingIntent)

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val channelId2 = "2"
                val channelName2 = "channel2"

                val channel = NotificationChannel(
                    channelId2,
                    channelName2,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(true)
                channel.setShowBadge(true)

                mBuilder.setChannelId(channelId2)

                mNotificationManager?.createNotificationChannel(channel)
            }else{
                mBuilder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            mNotificationManager?.notify(notifyId, mBuilder.build())
        }

        /*********** Large Text Notification ***************/
        fun largeTextNotification(context: Context, title: String, content: String){
            val notifyId = 300

            val bigIcon =
                BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round)

            val bigTextStyle = NotificationCompat.BigTextStyle()
            bigTextStyle.bigText(content)
            bigTextStyle.setSummaryText("Android with Kotlin")

            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setLargeIcon(bigIcon)
                .setStyle(bigTextStyle)

            val intent = Intent(context, NotificationAfterActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            mBuilder.setContentIntent(pendingIntent)

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val channelId3 = "3"
                val channelName3 = "channel3"

                val channel = NotificationChannel(
                    channelId3,
                    channelName3,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(true)
                channel.setShowBadge(true)

                mBuilder.setChannelId(channelId3)

                mNotificationManager?.createNotificationChannel(channel)
            }else{
                mBuilder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            mNotificationManager?.notify(notifyId, mBuilder.build())

        }

        /********* Image Notification ***********/
        fun imageNotification(context: Context, title: String){
            val notifyId = 4

            val bigPictureStyle = NotificationCompat.BigPictureStyle()
            bigPictureStyle.bigPicture(BitmapFactory.decodeResource(context.resources, R.drawable.senery)).build()

            val intent = Intent(context, NotificationAfterActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .addAction(android.R.drawable.ic_menu_share, "Share", pendingIntent)
                .setStyle(bigPictureStyle)

            mBuilder.setContentIntent(pendingIntent)

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val channelId4 = "4"
                val channelName4 = "channel4"

                val channel = NotificationChannel(
                    channelId4,
                    channelName4,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(true)
                channel.setShowBadge(true)

                mBuilder.setChannelId(channelId4)

                mNotificationManager?.createNotificationChannel(channel)
            }else{
                mBuilder.setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
            }

            mNotificationManager?.notify(notifyId, mBuilder.build())

        }

    }
}