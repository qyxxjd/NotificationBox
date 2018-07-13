package com.classic.box.crash

/**
 * Crash事件回调
 *
 * @author classic
 * @version v1.0, 2018/6/25 上午11:46
 */
@Suppress("unused")
interface ICrashProcess {

    fun onException(thread: Thread, exception: Throwable)
}
