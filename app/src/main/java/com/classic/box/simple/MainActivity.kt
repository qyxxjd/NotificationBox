package com.classic.box.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import com.classic.box.Config
import com.classic.box.NotificationBox

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(com.classic.box.R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.app_name)

        NotificationBox.apply(Config().installCrashMonitor(this))

        findViewById<Button>(R.id.button).setOnClickListener {
            val text: String? = null
            print(text!!.length)
        }
    }
}
