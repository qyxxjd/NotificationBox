@file:Suppress("MemberVisibilityCanBePrivate")

package com.classic.box

import com.classic.box.crash.CrashHandler
import com.classic.box.utils.BoxUtil

/**
 * 通知盒子
 *
 * @author classic
 * @version v1.0, 2018/6/25 上午11:46
 */
class NotificationBox {
    companion object {
        private var mConfig: Config? = null
        fun apply(config: Config) {
            mConfig = config

            if (mConfig!!.hasInstallCrashMonitor) {
                BoxUtil.checkDir(getCrashSavePath())
                BoxUtil.checkDir(getOomSavePath())
                CrashHandler(mConfig!!.mCrashProcess!!)
            }
        }

        fun getCrashSavePath(): String {
            checkConfig()
            return mConfig!!.mCrashPath?:BoxUtil.getCrashDir()
        }

        fun getOomSavePath(): String {
            return BoxUtil.getOomDir()
        }

        private fun checkConfig() {
            if (null == mConfig) {
                throw NullPointerException("The configuration item is not initialized")
            }
        }
    }
}