package com.costular.atomtasks.data.di

import android.content.Context
import com.costular.atomtasks.data.manager.ReminderManager
import com.costular.atomtasks.data.manager.ReminderManagerImpl
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
    ): ReminderManager = ReminderManagerImpl(context)
}
