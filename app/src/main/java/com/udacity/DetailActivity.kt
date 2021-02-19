package com.udacity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var motionLayout: MotionLayout
    private var status: String? = ""
    private var url: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras!!
        status = extras.getString("status")
        url = extras.getString("url")
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        val constraintLayout: ConstraintLayout = findViewById(R.id.include)
        val statusView: TextView = constraintLayout.findViewById(R.id.status)
        statusView.text = status
        val urlView: TextView = constraintLayout.findViewById(R.id.fileName)
        urlView.text = url

        button1 = findViewById(R.id.buttonTest)
        /*motionLayout = findViewById(R.id.activityDetailMotionLayout)
        motionLayout.setTransitionDuration(3000)
        motionLayout.transitionToEnd()*/



        button1.setOnClickListener {
            val intent = Intent(this.applicationContext, MainActivity::class.java);
            startActivity(intent);
        }
    }
}
