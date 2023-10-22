package com.costular.atomtasks.data.tasks

import com.costular.atomtasks.data.database.AtomTasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesTaskDao(
        database: AtomTasksDatabase,
    ): TasksDao = database.getTasksDao()

    @Provides
    fun providesRemindersDao(
        database: AtomTasksDatabase,
    ): ReminderDao = database.getRemindersDao()
}
