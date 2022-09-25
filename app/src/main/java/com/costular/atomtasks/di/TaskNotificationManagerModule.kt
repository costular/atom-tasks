package com.costular.atomtasks.di

import android.content.Context
import com.costular.atomtasks.data.TaskNotificationManagerImpl
import com.costular.atomtasks.tasks.manager.TaskNotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class TaskNotificationManagerModule {

    @Provides
    fun provideTaskNotificationManager(
        @ApplicationContext context: Context,
    ): TaskNotificationManager = TaskNotificationManagerImpl(context)
}
