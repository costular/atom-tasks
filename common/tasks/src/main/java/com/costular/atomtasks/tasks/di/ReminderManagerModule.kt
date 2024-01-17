package com.costular.atomtasks.tasks.di

import android.content.Context
import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.tasks.helper.TaskReminderManagerImpl
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
