package com.udacity.util


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity
import com.udacity.MainActivity
import com.udacity.R


// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context, status: String, url: String) {
    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
            .putExtra("status", status)
            .putExtra("url", url)
    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val loaderImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.sample
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(loaderImage)
            .bigLargeIcon(null)

    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.loading_notification_channel_id)
    )

            .setSmallIcon(R.drawable.sample)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
            .setStyle(bigPicStyle)
            .setLargeIcon(loaderImage)

            .addAction(
                    R.drawable.sample,
                    applicationContext.getString(R.string.download_detail),
                    contentPendingIntent
            )

            .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(NOTIFICATION_ID, builder.build())
}

// TODO: Step 1.14 Cancel all notifications
/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
