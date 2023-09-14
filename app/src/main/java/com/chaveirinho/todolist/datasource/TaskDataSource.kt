package com.chaveirinho.todolist.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chaveirinho.todolist.model.Task

@Dao
interface TaskDataSource {
    @Query("SELECT * FROM Task")
    fun getAll(): List<Task>

    @Insert
    fun salve(vararg task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun altera(task: Task)
}
