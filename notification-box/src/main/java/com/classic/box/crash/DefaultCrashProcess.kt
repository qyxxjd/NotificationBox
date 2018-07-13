package com.classic.box.crash

import android.content.Context
import android.os.Debug
import android.util.Log

import com.classic.box.utils.BoxUtil
import com.classic.box.utils.NotifyUtil

import java.io.File
import java.io.IOException

/**
 * 默认的Crash处理
 *
 * @author classic
 * @version v1.0, 2018/6/25 上午11:46
 */
class DefaultCrashProcess(context: Context) : ICrashProcess {

    private val mContext: Context = context.applicationContext

    override fun onException(thread: Thread, exception: Throwable) {
        if (isOutOfMemoryError(exception)) {
            val file = File(BoxUtil.getOomDir(),
                    BoxUtil.createOomFileName())
            try {
                Debug.dumpHprofData(file.absolutePath)
            } catch (e: IOException) {
                Log.e("DefaultCrashProcess", "dump hprof error", e)
            }
        }

        val className = exception.javaClass.simpleName
        val file = BoxUtil.getCrashDir() + File.separator + className + "_" + System.currentTimeMillis() + ".txt"
        BoxUtil.saveCrash(exception, file)
        NotifyUtil.showNotification(mContext, className, exception.message?:"")
        Runtime.getRuntime().exit(1)
    }

    private fun isOutOfMemoryError(ex: Throwable): Boolean {
        if (ex is OutOfMemoryError) {
            return true
        }
        var t:Throwable?
        do {
            t = ex.cause
            if (t != null && t is OutOfMemoryError) {
                return true
            }
        } while (t != null)
        return false
    }
}
