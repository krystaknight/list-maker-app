package com.krystamiinch.listmaker

import android.os.Parcel
import android.os.Parcelable

class TaskItem(val taskItem: String, var checked: Boolean = false): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readBoolean()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskItem)
        parcel.writeBoolean(checked)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<TaskItem> {
        override fun createFromParcel(parcel: Parcel)=  TaskItem(parcel)

        override fun newArray(size: Int): Array<TaskItem?> {
            return arrayOfNulls(size)
        }
    }
}