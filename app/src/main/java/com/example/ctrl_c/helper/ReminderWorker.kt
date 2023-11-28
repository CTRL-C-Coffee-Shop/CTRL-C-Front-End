package com.example.ctrl_c.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.concurrent.TimeUnit
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.ctrl_c.R


class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams)  {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding channel"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("notification_pref", Context.MODE_PRIVATE)


    private var resultStatus: Result? = null

    override fun doWork(): Result {

        val lastNotificationTime = sharedPreferences.getLong("last_notification_time", 0)
        val currentTime = System.currentTimeMillis()

        //Changeable Timestamp
        if (currentTime - lastNotificationTime >= TimeUnit.SECONDS.toMillis(5)) {
            val title = applicationContext.getString(R.string.notification_title)
            val desc = applicationContext.getString(R.string.notification_message)
            showNotification(title, desc)

            with(sharedPreferences.edit()) {
                putLong("last_notification_time", currentTime)
                apply()
            }
        }
        return Result.success()
    }

    private fun showNotification(title: String, description: String?) : Result {
        resultStatus = Result.failure()
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
            resultStatus = Result.success()
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
        return resultStatus as Result
    }
}