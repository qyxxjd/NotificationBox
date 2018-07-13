package com.classic.box.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.classic.box.R
import com.classic.box.crash.CrashInfo
import com.classic.box.utils.BoxUtil
import java.io.File

class CrashActivity : AppCompatActivity() {

    private var mCrashInfo: CrashInfo? = null

    companion object {
        const val KEY_CRASH = "crash"
        fun start(context: Context, crashInfo: CrashInfo) {
            val intent = Intent(context, CrashActivity::class.java)
            intent.putExtra(KEY_CRASH, crashInfo)
            context.startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.box_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_delete -> {
                if (null != mCrashInfo) {
                    BoxUtil.deleteFile(mCrashInfo!!.file)
                    finish()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.box_crash_detail)
        if (!intent.hasExtra(KEY_CRASH)) {
            finish()
            return
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.box_crash_title)

        mCrashInfo = intent.getParcelableExtra(KEY_CRASH)
        readFile(mCrashInfo!!)
    }

    private fun readFile(crashInfo: CrashInfo) {
        Thread(Runnable {
            val text = BoxUtil.readFromFile(File(crashInfo.file))
            runOnUiThread { findViewById<TextView>(R.id.box_crash_detail_text).text = text }
        }).start()
    }
}
