package com.costular.atomreminders.data.tasks

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
abstract class TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addTask(taskEntity: TaskEntity): Long

    @Query("SELECT * FROM tasks")
    abstract fun getAllTasks(): Flow<List<TaskAggregated>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE date = :date")
    abstract fun getAllTasksForDate(date: LocalDate): Flow<List<TaskAggregated>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    abstract fun getTaskById(id: Long): Flow<TaskAggregated>

    @Query("DELETE FROM tasks WHERE id = :id")
    abstract suspend fun removeTaskById(id: Long)

}