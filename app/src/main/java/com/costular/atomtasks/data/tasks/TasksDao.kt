package com.costular.atomtasks.data.tasks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(taskEntity: TaskEntity): Long

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY is_done ASC")
    fun observeAllTasks(): Flow<List<TaskAggregated>>

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY is_done ASC")
    fun getAllTasks(): List<TaskAggregated>

    @Transaction
    @Query("SELECT * FROM tasks WHERE date = :date ORDER BY is_done ASC")
    fun getAllTasksForDate(date: LocalDate): Flow<List<TaskAggregated>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    fun getTaskById(id: Long): Flow<TaskAggregated>

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun removeTaskById(id: Long)

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :id")
    suspend fun updateTaskDone(id: Long, isDone: Boolean)

    @Query("UPDATE tasks SET name = :name, date = :day where id = :taskId")
    suspend fun updateTask(taskId: Long, day: LocalDate, name: String)
}
