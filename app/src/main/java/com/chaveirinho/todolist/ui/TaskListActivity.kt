package com.chaveirinho.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.chaveirinho.todolist.adapter.TaskListAdapter
import com.chaveirinho.todolist.databinding.ActivityListTaskBinding
import com.chaveirinho.todolist.datasource.AppDatabase

class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTaskBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTask.adapter = adapter
        configFab()
        setEmptyState()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.getInstance(this)
        val taskDataSource = db.TaskDataSource()
        adapter.atualiza(taskDataSource.getAll())
    }

    fun configFab() {
       binding.fabAddNewTask.setOnClickListener {
           val intent =Intent(this, AddTaskActivity::class.java)
           startActivity(intent)
       }
    }

    private fun setEmptyState() {
        if (adapter.itemCount > 0) {
            binding.includeEmpyte.empyteState.visibility = VISIBLE
        } else {
            binding.includeEmpyte.empyteState.visibility = INVISIBLE
        }
    }

}
