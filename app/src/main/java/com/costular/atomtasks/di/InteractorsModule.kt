package com.costular.atomtasks.di

import com.costular.atomtasks.domain.interactor.GetTaskByIdInteractor
import com.costular.atomtasks.domain.interactor.GetThemeUseCase
import com.costular.atomtasks.domain.interactor.SetThemeUseCase
import com.costular.atomtasks.domain.repository.SettingsRepository
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

    @Singleton
    @Provides
    fun provideGetThemeUseCase(settingsRepository: SettingsRepository): GetThemeUseCase =
        GetThemeUseCase(settingsRepository)

    @Singleton
    @Provides
    fun provideSetThemeUseCase(settingsRepository: SettingsRepository): SetThemeUseCase =
        SetThemeUseCase(settingsRepository)
}
