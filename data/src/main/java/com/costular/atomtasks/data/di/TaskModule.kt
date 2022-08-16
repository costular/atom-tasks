package com.costular.atomtasks.data.di

import com.costular.atomtasks.data.tasks.DefaultTasksRepository
import com.costular.atomtasks.data.tasks.TaskLocalDataSource
import com.costular.atomtasks.data.tasks.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    fun providesTaskRepository(
        taskLocalDataSource: TaskLocalDataSource
    ): TasksRepository = DefaultTasksRepository(taskLocalDataSource)

}
