package com.costular.atomtasks.tasks.di

import com.costular.atomtasks.tasks.repository.DefaultTasksRepository
import com.costular.atomtasks.tasks.repository.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface TaskRepositoryModule {
    @Binds
    fun provideTaskRepository(
        repository: DefaultTasksRepository,
    ): TasksRepository
}
