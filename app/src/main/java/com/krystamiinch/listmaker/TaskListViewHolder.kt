package com.krystamiinch.listmaker

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var taskCheckBox = itemView.findViewById<CheckBox>(R.id.taskDoneCheck)
    var taskTextView = itemView.findViewById<TextView>(R.id.taskTitle)
}