package com.costular.atomtasks.tasks.di

import com.costular.atomtasks.tasks.DefaultTasksRepository
import com.costular.atomtasks.tasks.TaskLocalDataSource
import com.costular.atomtasks.tasks.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class TaskRepositoryModule {

    @Provides
    fun provideTaskRepository(
        taskLocalDataSource: TaskLocalDataSource,
    ): TasksRepository = DefaultTasksRepository(taskLocalDataSource)
}
