package com.costular.atomtasks.di

import android.content.Context
import androidx.room.Room
import com.costular.atomtasks.db.AtomRemindersDatabase
import com.costular.atomtasks.data.tasks.*
import com.costular.atomtasks.domain.repository.TasksRepository
import com.costular.atomtasks.ui.common.validation.FieldValidator
import com.costular.atomtasks.ui.common.validation.FieldValidatorDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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