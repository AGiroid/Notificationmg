package com.example.notificationmg


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.example.notificationmg.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val mainBinding:ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_main)
    }
    val CHANNEL_ID = "ANDROID"
    val CHANNEL_NAME = "ANDROID ContentWriting"
    val CHANNEL_DESCRIPTION = "ANDROID NOTIFICATION"
    val link1 = "https://www.geeksforgeeks.org/"
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imgBitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_gfg)
        mainBinding.btnNotify.setOnClickListener {
            val title=mainBinding.edname.text.toString()
            val body=mainBinding.edbody.text.toString()
           val intent1=AndroidOpenIntent(link1)
            var pendingIntent1:PendingIntent?=null
            if(Build.VERSION.SDK_INT>=31){
                pendingIntent1 = PendingIntent.getActivity(this, 5, intent1, PendingIntent.FLAG_IMMUTABLE)
            }else{
                pendingIntent1 = PendingIntent.getActivity(this, 5, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            notificationChannel()
            val builder=NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent1)
                .setAutoCancel(true)
                .addAction(0, "LET CONTRIBUTE", pendingIntent1)
                .setLargeIcon(imgBitmap)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(imgBitmap)
                    .bigLargeIcon(null))
                .build()
            val nManager = NotificationManagerCompat.from(this)
            nManager.notify(1, builder)

        }
    }
    private fun notificationChannel() {
        // check if the version is equal or greater
        // than android oreo version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // creating notification channel and setting
            // the description of the channel
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = CHANNEL_DESCRIPTION
            }
            // registering the channel to the System
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun AndroidOpenIntent(link: String): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(link)
        return intent
    }
}