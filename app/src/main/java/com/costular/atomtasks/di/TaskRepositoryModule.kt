package com.costular.atomtasks.di

import com.costular.atomtasks.data.tasks.DefaultTasksRepository
import com.costular.atomtasks.data.tasks.TaskLocalDataSource
import com.costular.atomtasks.domain.repository.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class TaskRepositoryModule {

    @Singleton
    @Provides
    fun provideTaskRepository(localDataSource: TaskLocalDataSource): TasksRepository =
        DefaultTasksRepository(localDataSource)

}