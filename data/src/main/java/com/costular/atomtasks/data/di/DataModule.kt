package com.costular.atomtasks.data.di

import com.costular.atomtasks.data.tasks.DefaultTasksLocalDataSource
import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.TaskLocalDataSource
import com.costular.atomtasks.data.tasks.TasksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesTaskLocalDataSource(
        tasksDao: TasksDao,
        reminderDao: ReminderDao,
    ): TaskLocalDataSource =
        DefaultTasksLocalDataSource(tasksDao, reminderDao)
}
