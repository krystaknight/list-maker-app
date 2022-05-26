package com.krystamiinch.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TodoListFragment.OnFragmentInteractionListener {

    private var todoListFragment = TodoListFragment.newInstance()

    companion object{
        const val INTENT_LIST_KEY = "List"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ ->
            showCreateTodoListDialog()
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container,todoListFragment)
            .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showCreateTodoListDialog(){
        val dialogTitle = getString(R.string.name_of_list)
        val positiveTitle = getString(R.string.create)
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)

        myDialog.setPositiveButton(positiveTitle){
            dialog, _ ->
            val list = TaskList(todoTitleEditText.text.toString())
            todoListFragment.addList(list)
            dialog.dismiss()
            showTaskListItems(list)
        }
        myDialog.create().show()
    }

    private fun showTaskListItems(list: TaskList){
        val bundle = Bundle()
        bundle.putParcelable(INTENT_LIST_KEY, list)
        val detailFrag  = DetailFragment.newInstance()
        detailFrag.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,detailFrag)
            .addToBackStack(null)
            .commit()
        fab.hide()
    }

    override fun onTodoListClicked(list: TaskList) {
        showTaskListItems(list)
    }


}
