package com.chaveirinho.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaveirinho.todolist.databinding.ActivityAddTaskBinding
import com.chaveirinho.todolist.datasource.AppDatabase
import com.chaveirinho.todolist.extensions.format
import com.chaveirinho.todolist.extensions.text
import com.chaveirinho.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var idTask = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertListeners()

        intent.getParcelableExtra<Task>(CHAVE_TASK).let { taskCarregada ->
            idTask = taskCarregada?.id!!
            binding.tilTitulo.text = taskCarregada.title
            binding.tilDate.text = taskCarregada.date
            binding.tilHour.text = taskCarregada.hour
        }
    }

    // adicionando calendario (datePicker)
    private fun insertListeners() {
        val db = AppDatabase.getInstance(this)
        val taskDataSource = db.TaskDataSource()
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            // adicionando função ao botão positivo do calendario
            datePicker.addOnPositiveButtonClickListener {
                // Usando time zone para pegar a data corretamente
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offSet).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val newTask = createNewTask()
            if (idTask > 0) {
                taskDataSource.altera(newTask)
            } else {
                taskDataSource.salve(newTask)
            }
            finish()
        }
    }

    private fun createNewTask(): Task {
        val title = binding.tilTitulo.text
        val date = binding.tilDate.text
        val hour = binding.tilHour.text
        val id = idTask
        return Task(
            title = title,
            date = date,
            hour = hour,
            id = id,
        )
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}
