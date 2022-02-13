package com.costular.atomtasks.di

import com.costular.atomtasks.data.tasks.DefaultTasksLocalDataSource
import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.TaskLocalDataSource
import com.costular.atomtasks.data.tasks.TasksDao
import com.costular.atomtasks.db.AtomRemindersDatabase
import com.costular.atomtasks.ui.common.validation.FieldValidator
import com.costular.atomtasks.ui.common.validation.FieldValidatorDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun providesTasksDao(db: AtomRemindersDatabase): TasksDao = db.getTasksDao()

    @Singleton
    @Provides
    fun providesReminderDao(db: AtomRemindersDatabase): ReminderDao = db.getRemindersDao()

    @Singleton
    @Provides
    fun providesTaskLocalDataSource(
        tasksDao: TasksDao,
        reminderDao: ReminderDao,
    ): TaskLocalDataSource =
        DefaultTasksLocalDataSource(tasksDao, reminderDao)

    @Singleton
    @Provides
    fun provideFieldValidator(): FieldValidator = FieldValidatorDefault()
}
