@file:Suppress("unused")

package com.classic.box

import android.content.Context
import com.classic.box.crash.DefaultCrashProcess
import com.classic.box.crash.ICrashProcess

/**
 * 全局配置
 *
 * @author classic
 * @version v1.0, 2018/6/27 下午5:53
 */
class Config {
    var mCrashPath: String? = null
    var mCrashProcess: ICrashProcess? = null
    var hasInstallCrashMonitor: Boolean = false

    /**
     * 安装崩溃监控模块
     *
     * @param context Context
     */
    fun installCrashMonitor(context: Context): Config {
        mCrashProcess = DefaultCrashProcess(context)
        hasInstallCrashMonitor = true
        return this
    }

    /**
     * 安装崩溃监控模块
     *
     * @param path 可选参数。自定义崩溃文件存放路径(需要适配FileProvider)
     * @param crashProcess 可选参数。自定义崩溃处理
     */
    fun installCrashMonitor(path: String, crashProcess: ICrashProcess): Config {
        mCrashPath = path
        mCrashProcess = crashProcess
        hasInstallCrashMonitor = true
        return this
    }
}