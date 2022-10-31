package com.costular.atomtasks.tasks.di

import android.content.Context
import com.costular.atomtasks.tasks.manager.TaskReminderManager
import com.costular.atomtasks.tasks.manager.TaskReminderManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReminderManagerModule {

    @Provides
    fun providesReminderManager(
        @ApplicationContext context: Context,
    ): TaskReminderManager = TaskReminderManagerImpl(context)
}
