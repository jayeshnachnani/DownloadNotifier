package com.udacity

//import android.view.View.VISIBLE
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
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.udacity.util.sendNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private lateinit var button1: Button
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    lateinit var downloadManager: DownloadManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        radioGroup = findViewById(R.id.radioGroup)
        //button1 = findViewById(R.id.button1)
        Timber.plant(Timber.DebugTree())
        notificationManager = getSystemService(
            NotificationManager::class.java
        )
        createChannel(
            getString(R.string.loading_notification_channel_id),
            getString(R.string.loading_notification_channel_name)
        )

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        //registerReceiver(receiver, IntentFilter(DownloadManager.STATUS_FAILED.toString()))


        custom_button.setOnClickListener {
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId

            if (selectedOption > 0) {
                // Assigning id of the checked radio button
                radioButton = findViewById(selectedOption)!!
                Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
                URL = radioButton.text as String
                //fader()
                custom_button.buttonState = ButtonState.Loading
                Timber.i("download started")
                download()
                Timber.i("After download")
            }
            else{
                Toast.makeText(baseContext, getString(R.string.select_repository), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (downloadID == id) {
                val query = id?.let { DownloadManager.Query().setFilterById(it) }
                val cursor = downloadManager.query(query)
                cursor.moveToFirst()
                val status = when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_SUCCESSFUL -> "SUCCESS"
                    else -> "FAILED"
                }
                Toast.makeText(baseContext, "Download Completed", Toast.LENGTH_SHORT).show()
                custom_button.buttonState = ButtonState.Completed
                notificationManager.sendNotification("Download Completed",applicationContext,status,radioButton.text.toString())
            }
        }
    }

    private fun download() {
        //URL = ""
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    companion object {
        private var URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
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
        animator.end()
    }

}
