package com.costular.atomtasks.tasks.di

import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceManager
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceManagerImpl
import com.costular.atomtasks.tasks.repository.DefaultTasksLocalDataSource
import com.costular.atomtasks.tasks.repository.TaskLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface TasksModule {
    @Binds
    fun bindsTaskLocalDataSource(
        defaultTasksLocalDataSource: DefaultTasksLocalDataSource,
    ): TaskLocalDataSource

    @Binds
    fun bindsRecurrenceManager(
        recurrenceManagerImpl: RecurrenceManagerImpl
    ): RecurrenceManager
}
