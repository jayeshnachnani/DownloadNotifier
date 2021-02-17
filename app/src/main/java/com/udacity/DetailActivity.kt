package com.udacity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var motionLayout: MotionLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        button1 = findViewById(R.id.buttonTest)
        //motionLayout = findViewById(R.id.activityDetailMotionLayout)
        //motionLayout.setTransitionDuration(3000)
        //motionLayout.transitionToEnd()



        button1.setOnClickListener {
            val intent = Intent(this.applicationContext, MainActivity::class.java);
            startActivity(intent);
        }
    }
}
