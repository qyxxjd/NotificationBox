package com.classic.box.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.classic.box.R
import com.classic.box.ui.NotificationBoxActivity

/**
 * 通知工具类
 *
 * @author classic
 * @version v1.0, 2018/6/27 下午5:39
 */
object NotifyUtil {
    private const val CHANNEL_ID = "NotificationBoxChannel"
    private const val CHANNEL_NAME = "Primary Channel"

    private var sNotificationId = 1

    /**
     * 显示通知
     *
     * @param title 通知标题
     * @param content 通知内容
     */
    fun showNotification(context: Context, title: String, content: String, icon: Int = smallIcon) {
        val intent = Intent(context, NotificationBoxActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Support android 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH)
            channel.lightColor = Color.YELLOW
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(sNotificationId, builder.build())
        ++sNotificationId
    }

    /*
     * mdpi  : 24
     * hdpi  : 36
     * xhdpi : 48
     * xxhdpi: 72
     */
    private val smallIcon: Int
        get() = R.drawable.ic_notify_logo
}