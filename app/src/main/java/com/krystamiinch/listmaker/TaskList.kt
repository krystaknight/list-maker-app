package com.krystamiinch.listmaker

import android.os.Parcel
import android.os.Parcelable

class TaskList(val name: String, val tasks: ArrayList<TaskItem> = ArrayList()): Parcelable {

    constructor(parcel:Parcel) : this(
        parcel.readString()!!,
        parcel.createTypedArrayList(TaskItem.CREATOR)!!
    )

    companion object CREATOR: Parcelable.Creator<TaskList>{
        override fun createFromParcel(source: Parcel): TaskList = TaskList(source)

        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)

    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flag: Int) {
        dest.writeString(name)
        dest.writeTypedList(tasks)
    }


}