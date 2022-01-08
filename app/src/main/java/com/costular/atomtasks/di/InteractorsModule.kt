package com.costular.atomtasks.di

import com.costular.atomtasks.domain.interactor.GetTaskByIdInteractor
import com.costular.atomtasks.domain.repository.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class InteractorsModule {

    @Singleton
    @Provides
    fun provideGetTaskByIdInteractor(tasksRepository: TasksRepository): GetTaskByIdInteractor =
        GetTaskByIdInteractor(tasksRepository)

}