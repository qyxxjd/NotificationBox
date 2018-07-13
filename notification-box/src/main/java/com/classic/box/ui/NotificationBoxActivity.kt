package com.classic.box.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.classic.adapter.BaseAdapterHelper
import com.classic.adapter.CommonRecyclerAdapter
import com.classic.box.R
import com.classic.box.crash.CrashInfo
import com.classic.box.utils.BoxUtil
import java.io.File

class NotificationBoxActivity : AppCompatActivity() {

    private var mBoxAdapter: BoxAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.box_main)

        val toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setTitle(R.string.box_name)

        val recyclerView: RecyclerView = findViewById(R.id.box_main_recycler_view)
        val context: Context = recyclerView.context
        recyclerView.layoutManager = LinearLayoutManager(context)

        mBoxAdapter = BoxAdapter(context)
        recyclerView.adapter = mBoxAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mBoxAdapter!!.setOnItemClickListener { _, _, position -> CrashActivity.start(context, mBoxAdapter!!.getItem(position)) }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        Thread(Runnable {
            val files: Array<File> = File(BoxUtil.getCrashDir()).listFiles()
            if (files.isNotEmpty()) {
                val crashFiles: MutableList<CrashInfo> = mutableListOf()
                for (item in files) {
                    if (item.length() > 0) crashFiles.add(BoxUtil.toCrashInfo(item))
                }
                crashFiles.sort()
                runOnUiThread { mBoxAdapter!!.replaceAll(crashFiles) }
            } else {
                runOnUiThread { mBoxAdapter!!.clear() }
            }
        }).start()
    }

    inner class BoxAdapter
    @JvmOverloads
    constructor(context: Context, layout: Int = R.layout.box_item_crash, list: MutableList<CrashInfo>? = null):
            CommonRecyclerAdapter<CrashInfo>(context, layout, list) {

        override fun onUpdate(helper: BaseAdapterHelper?, item: CrashInfo?, position: Int) {
            if (null != item) {
                helper!!.setText(R.id.box_item_crash_title, item.title)
                        .setText(R.id.box_item_crash_time, BoxUtil.time(item.time, BoxUtil.FORMAT_DATA_TIME))
                        .setText(R.id.box_item_crash_content, item.describe)
            }
        }

    }
}
