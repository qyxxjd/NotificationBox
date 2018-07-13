package com.classic.box.crash

/**
 * Crash包装类
 *
 * @author classic
 * @version v1.0, 2018/6/25 上午11:46
 */
class CrashHandler(private val mCrashProcess: ICrashProcess) : Thread.UncaughtExceptionHandler {
    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
    override fun uncaughtException(thread: Thread, exception: Throwable) {
        mCrashProcess.onException(thread, exception)
    }
}
