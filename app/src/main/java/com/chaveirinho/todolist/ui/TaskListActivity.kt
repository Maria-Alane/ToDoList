package com.chaveirinho.todolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chaveirinho.todolist.databinding.ActivityListTaskBinding
import com.chaveirinho.todolist.datasource.AppDatabase
import com.chaveirinho.todolist.datasource.TaskDataSource

class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTaskBinding
    private val adapter by lazy { TaskListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configRecycler()
        configFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.getInstance(this)
        val taskDataSource = db.TaskDataSource()
        adapter.atualiza(taskDataSource.getAll())
    }

    private fun configRecycler() {
        val recyclerView = binding.rvTask
        recyclerView.adapter = adapter
    }

    fun configFab() {
        val fab = binding.fabAdd
        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

}
