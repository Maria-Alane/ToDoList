package com.chaveirinho.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaveirinho.todolist.databinding.ActivityAddTaskBinding
import com.chaveirinho.todolist.extensions.format
import com.chaveirinho.todolist.extensions.text
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    //adicionando calendario (datePicker)
    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            //adicionando função ao botão positivo do calendario
            datePicker.addOnPositiveButtonClickListener {
                //Usando time zone para pegar a data corretamente
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
                binding.tilHour.text = "${timePicker.hour} ${timePicker.minute}"
            }
            timePicker.show(supportFragmentManager, null)
        }
    }

}