package com.krystamiinch.listmaker

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(val items: ArrayList<TaskItem>, val updateClickListener: TaskListItemUpdateClickListener): RecyclerView.Adapter<TaskListViewHolder>() {

    interface TaskListItemUpdateClickListener{
        fun updateTaskItem(taskItem: ArrayList<TaskItem>)
    }

    fun addItem(item: TaskItem){
        items.add(item)
        notifyItemChanged(items.size -1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_view_holder,parent, false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.taskCheckBox.isChecked = items[position].checked
        holder.taskTextView.text = items[position].taskItem
        if(items[position].checked){
            holder.taskCheckBox.isChecked = true
            holder.taskTextView.apply{
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
       // holder.taskCheckBox.
        holder.itemView.setOnClickListener{
            checkbox(holder, position)
        }

    }
    fun checkbox(holder: TaskListViewHolder, position: Int){
        if(!holder.taskCheckBox.isChecked){
            holder.taskCheckBox.isChecked = true
            items[position].checked = true
            holder.taskTextView.apply{
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        } else {
            holder.taskCheckBox.isChecked = false
            items[position].checked = false
            holder.taskTextView.apply{
                paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
        updateClickListener.updateTaskItem(items)
    }

    override fun getItemCount()= items.size
}