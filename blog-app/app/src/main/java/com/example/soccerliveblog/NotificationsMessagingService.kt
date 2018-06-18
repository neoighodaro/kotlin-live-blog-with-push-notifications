package com.example.soccerliveblog


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.fcm.MessagingService

class NotificationsMessagingService : MessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val notificationId = 10
        val channelId  = "soccer-channel"

        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        lateinit var channel:NotificationChannel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            channel = NotificationChannel("world-cup", name, importance)
            channel.description = description
            notificationManager!!.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val mBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.notification!!.title!!)
                .setContentText(remoteMessage.notification!!.body!!)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        notificationManager.notify(notificationId, mBuilder.build())

    }

}