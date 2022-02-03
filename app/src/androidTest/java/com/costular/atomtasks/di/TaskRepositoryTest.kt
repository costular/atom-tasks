package com.costular.atomtasks.di

import com.costular.atomtasks.data.tasks.DefaultTasksRepository
import com.costular.atomtasks.data.tasks.TaskLocalDataSource
import com.costular.atomtasks.domain.repository.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [TaskRepositoryModule::class]
)
class TaskRepositoryTest {

    @Singleton
    @Provides
    fun provideTaskRepositoryTest(): TasksRepository = mockk()

}