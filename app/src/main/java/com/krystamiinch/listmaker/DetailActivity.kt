package com.krystamiinch.listmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity(), TaskListAdapter.TaskListItemUpdateClickListener {

    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var taskFab: FloatingActionButton
    private val listDataManager:ListDataManager = ListDataManager(this)
    private var taskItemList = ArrayList<TaskItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        title =list.name
        taskItemList = listDataManager.readTaskList(list.name)

        taskListRecyclerView = findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)
        taskListRecyclerView.adapter = TaskListAdapter(taskItemList, this)
        taskFab = findViewById(R.id.taskFab)

        taskFab.setOnClickListener { _ ->
            showCreateTodoListDialog()        }
    }

    private fun showCreateTodoListDialog(){
        val dialogTitle = getString(R.string.enter_task_item)
        val positiveTitle = getString(R.string.create)
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)


        myDialog.setPositiveButton(positiveTitle){
                dialog, _ ->
            val adapter = taskListRecyclerView.adapter as TaskListAdapter
            val task = todoTitleEditText.text.toString()
            val taskItem = TaskItem(task)
            list.tasks.add(taskItem)
            listDataManager.saveList(list)
            adapter.addItem(taskItem)
            dialog.dismiss()
        }
        myDialog.create().show()
    }

    override fun updateTaskItem(taskItem: ArrayList<TaskItem>) {
        listDataManager.saveList(TaskList(list.name, taskItem))
    }

}