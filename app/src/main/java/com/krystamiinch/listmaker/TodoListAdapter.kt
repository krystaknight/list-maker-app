package com.krystamiinch.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(val lists: ArrayList<TaskList>, val clickListener: TodoListClickListener, val deleteClickListener: DeleteClickListener): RecyclerView.Adapter<TodoListViewHolder>() {

    interface TodoListClickListener {
        fun listItemClicked(list: TaskList)
    }
    interface DeleteClickListener {
        fun deleteTaskList(listName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_view_holder,parent,false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {

        holder.listPositionTextView.text = (position+1).toString()
        holder.listTitleTextView.text = lists[position].name
        holder.itemView.setOnClickListener{
            clickListener.listItemClicked(lists[position])
        }
        holder.deleteButton.setOnClickListener {
            deleteClickListener.deleteTaskList(lists[position].name)
            deleteList(position)
        }

    }

    override fun getItemCount(): Int {
       return lists.size
    }

    fun addList(list: TaskList) {
        lists.add(list)
        notifyItemInserted(lists.size-1)
    }

    private fun deleteList(position: Int) {
       lists.removeAt(position)
        notifyDataSetChanged()
    }

}