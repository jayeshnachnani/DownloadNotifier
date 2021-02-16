package com.udacity

import android.animation.ObjectAnimator
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.ColorFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
//import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.udacity.util.sendNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private lateinit var button1: Button
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        radioGroup = findViewById(R.id.radioGroup)
        button1 = findViewById(R.id.button1)
        notificationManager = getSystemService(
            NotificationManager::class.java
        )
        createChannel(
            getString(R.string.loading_notification_channel_id),
            getString(R.string.loading_notification_channel_name)
        )

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            //custom_button.isClickable = true
            //this.titleColor = Color.RED
            //it.setBackgroundColor(20)
            //custom_button.isInvisible = true
            //download()
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            // Assigning id of the checked radio button
            radioButton = findViewById(selectedOption)
            it.setBackgroundColor(0xffffff00.toInt())
            //it.animate().start()
            //this.titleColor = 0x0000ffff.toInt()
            // Displaying text of the checked radio button in the form of toast
            Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
            fader()
        }

        button1.setOnClickListener {
            /*val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.sendNotification(applicationContext.getString(R.string.notification_title), applicationContext)*/

            /*createChannel(
                getString(R.string.loading_notification_channel_id),
                getString(R.string.loading_notification_channel_name)
            )*/
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            // Assigning id of the checked radio button
            radioButton = findViewById(selectedOption)
            // Displaying text of the checked radio button in the form of toast
            Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
            //fader()
            notificationManager.sendNotification("timepass",applicationContext)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {

                Toast.makeText(baseContext, "Download Completed", Toast.LENGTH_SHORT).show();
                notificationManager.sendNotification("Download Completed",applicationContext)
            }
        }
    }

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    companion object {
        private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }

    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_LOW
            )
            // TODO: Step 2.6 disable badges for this channel

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)


            // TODO: Step 1.6 END create channel

        }
    }

    private fun fader() {

        // Fade the view out to completely transparent and then back to completely opaque

        val animator = ObjectAnimator.ofFloat(custom_button, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.duration = 3000
        //animator.disableViewDuringAnimation(fadeButton)
        animator.start()
    }

}
