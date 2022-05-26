package com.krystamiinch.listmaker

import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailFragment : Fragment(), TaskListAdapter.TaskListItemUpdateClickListener {
    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var taskFab: FloatingActionButton
    lateinit var listDataManager:ListDataManager
    private var taskItemList = ArrayList<TaskItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)!!
        requireActivity().title =list.name
        taskItemList = listDataManager.readTaskList(list.name)

        taskListRecyclerView = view.findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(activity)
        taskListRecyclerView.adapter = TaskListAdapter(taskItemList, this)
        taskFab = view.findViewById(R.id.taskFab)
        taskFab.setOnClickListener { _ ->
            showCreateTodoListDialog(requireActivity().applicationContext)        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listDataManager = ListDataManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    private fun showCreateTodoListDialog(context: Context){
        val dialogTitle = getString(R.string.enter_task_item)
        val positiveTitle = getString(R.string.create)
        val myDialog = AlertDialog.Builder(requireContext())
        val todoTitleEditText = EditText(getContext())
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