package com.costular.atomtasks.data.tasks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addTask(taskEntity: TaskEntity): Long

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY is_done ASC")
    abstract fun observeAllTasks(): Flow<List<TaskAggregated>>

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY is_done ASC")
    abstract fun getAllTasks(): List<TaskAggregated>

    @Transaction
    @Query("SELECT * FROM tasks WHERE date = :date ORDER BY is_done ASC")
    abstract fun getAllTasksForDate(date: LocalDate): Flow<List<TaskAggregated>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    abstract fun getTaskById(id: Long): Flow<TaskAggregated>

    @Query("DELETE FROM tasks WHERE id = :id")
    abstract suspend fun removeTaskById(id: Long)

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :id")
    abstract suspend fun updateTaskDone(id: Long, isDone: Boolean)
}
