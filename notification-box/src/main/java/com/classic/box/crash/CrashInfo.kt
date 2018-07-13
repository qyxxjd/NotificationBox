package com.classic.box.crash

import android.os.Parcel
import android.os.Parcelable

/**
 * 崩溃信息
 *
 * @author classic
 * @version v1.0, 2018/6/27 上午9:40
 */
data class CrashInfo (
        var file: String = "",
        var title: String = "",
        var time: Long = 0L,
        var describe: String = "") : Parcelable, Comparable<CrashInfo> {

    // 从大到小排列
    override fun compareTo(other: CrashInfo): Int {
        return when {
            this.time == other.time -> 0
            this.time > other.time -> -1
            else -> 1
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(file)
        parcel.writeString(title)
        parcel.writeLong(time)
        parcel.writeString(describe)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CrashInfo> {
        override fun createFromParcel(parcel: Parcel): CrashInfo {
            return CrashInfo(parcel)
        }

        override fun newArray(size: Int): Array<CrashInfo?> {
            return arrayOfNulls(size)
        }
    }
}