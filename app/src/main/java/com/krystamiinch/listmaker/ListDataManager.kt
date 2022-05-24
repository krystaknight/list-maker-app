package com.krystamiinch.listmaker

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson

class ListDataManager(private val context: Context) {

    fun saveList(list:TaskList){
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        val gson =Gson()
        val taskLists = gson.toJson(list. tasks)
        sharedPrefs.putString(list.name, taskLists)
        sharedPrefs.apply()
    }

    fun readLists():ArrayList<TaskList>{
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val contents = sharedPrefs.all
        val taskLists = ArrayList<TaskList>()

        for(taskList in contents){

            val gson = Gson()
            val taskItemList = ArrayList<TaskItem>()
            val jsonText = sharedPrefs.getString(taskList.key, "")
            val taskListArray = gson.fromJson(jsonText,ArrayList::class.java)
            for(item in taskListArray){
                val jsonObjectTaskItem = gson.toJsonTree(item).asJsonObject
                val taskItem = gson.fromJson(jsonObjectTaskItem,TaskItem::class.java)
                taskItemList.add(taskItem)
            }

            val list = TaskList(taskList.key, taskItemList)
            taskLists.add(list)

        }
        return taskLists
    }

    fun readTaskList(listName: String):ArrayList<TaskItem>{
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val taskList = sharedPrefs.getString(listName, null)
        val taskItemList = ArrayList<TaskItem>()
        val gson = Gson()
        val taskItems = gson.fromJson(taskList,ArrayList::class.java)
        for(item in taskItems){
            val jsonObjectTaskItem = gson.toJsonTree(item).asJsonObject
            val taskItem = gson.fromJson(jsonObjectTaskItem,TaskItem::class.java)
            taskItemList.add(taskItem)
        }
        return taskItemList
    }

    fun deleteToDoList(listName: String) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.remove(listName).apply()
    }

}