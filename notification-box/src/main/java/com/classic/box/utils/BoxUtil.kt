package com.classic.box.utils

import android.os.Environment
import com.classic.box.crash.CrashInfo
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * 工具类
 *
 * @author classic
 * @version v1.0, 2018/6/25 下午3:28
 */
@Suppress("unused")
object BoxUtil {
    const val FORMAT_DATA_TIME: String = "yyyy-MM-dd HH:mm:ss"
    private const val FORMAT: String = "yyyyMMdd_HHmmss"
    private const val DIR_ROOT: String = "NotificationBox"
    private const val DIR_CRASH: String = "crash"
    private const val DIR_OOM: String = "oom"
    private const val OOM_PREFIX = "oom_"
    private const val OOM_SUFFIX = ".hprof"

    private fun getRootDir(): String = Environment.getExternalStorageDirectory().toString() +
            File.separator + DIR_ROOT

    fun checkDir(path: String) {
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    fun getCrashDir(): String = getRootDir() + File.separator + DIR_CRASH

    fun getOomDir(): String = getRootDir() + File.separator + DIR_OOM

    fun createOomFileName(): String = OOM_PREFIX + time() + OOM_SUFFIX

    fun createOomFileName(prefix: String, suffix: String): String = prefix + time() + suffix

    fun time(): String = time(System.currentTimeMillis())

    fun time(value: Long): String = time(value, FORMAT)

    fun time(value: Long, format: String): String = SimpleDateFormat(format, Locale.CHINA).format(Date(value)).toString()

    fun saveCrash(t: Throwable, file: String) {
        Thread(Runnable {
            writeToFile(file, getStackTrace(t))
        }).start()
    }

    fun deleteFile(path: String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }

    fun readFromFile(file: File): String {
        val crash = StringBuilder()
        try {
            val reader = BufferedReader(FileReader(file))
            var line: String?
            do {
                line = reader.readLine()
                if (line != null) {
                    crash.append(line)
                    crash.append('\n')
                }
            } while (line != null)
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return crash.toString()
    }

    private fun writeToFile(file: String, content: String) {
        val bufferedWriter: BufferedWriter
        try {
            bufferedWriter = BufferedWriter(FileWriter(file))
            bufferedWriter.write(content)
            bufferedWriter.flush()
            bufferedWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readFirstLineFromFile(file: File): String {
        var line = ""
        try {
            val reader = BufferedReader(FileReader(file))
            line = reader.readLine()
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return line
    }

    private fun getStackTrace(e: Throwable): String {
        val result = StringWriter()
        val printWriter = PrintWriter(result)
        e.printStackTrace(printWriter)
        val crashLog = result.toString()
        printWriter.close()
        return crashLog
    }

    fun toCrashInfo(file: File): CrashInfo {
        val array = file.name.split("_")
        val crashName = array[0]
        val time = array[1].substring(0, array[1].indexOf('.')).toLong()
        var desc = readFirstLineFromFile(file)
        if (desc.indexOf(':') > -1) {
            desc = desc.split(':')[1]
        }
        return CrashInfo(file.absolutePath, crashName, time, desc)
    }
}