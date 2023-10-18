package com.costular.atomtasks.tasks.di

import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.TasksDao
import com.costular.atomtasks.tasks.repository.DefaultTasksLocalDataSource
import com.costular.atomtasks.tasks.repository.TaskLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object TasksModule {
    @Provides
    fun providesTaskLocalDataSource(
        tasksDao: TasksDao,
        reminderDao: ReminderDao,
    ): TaskLocalDataSource = DefaultTasksLocalDataSource(tasksDao, reminderDao)
}
