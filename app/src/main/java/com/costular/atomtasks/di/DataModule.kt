package com.costular.atomtasks.di

import android.content.Context
import androidx.room.Room
import com.costular.atomtasks.db.AtomTasks
import com.costular.atomtasks.data.tasks.*
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
    fun provideDatabase(@ApplicationContext context: Context): AtomTasks =
        Room.databaseBuilder(context, AtomTasks::class.java, "atomtasks.db")
            .build()

    @Singleton
    @Provides
    fun providesTasksDao(db: AtomTasks): TasksDao = db.getTasksDao()

    @Singleton
    @Provides
    fun providesReminderDao(db: AtomTasks): ReminderDao = db.getRemindersDao()

    @Singleton
    @Provides
    fun providesTaskLocalDataSource(
        tasksDao: TasksDao,
        reminderDao: ReminderDao,
    ): TaskLocalDataSource =
        DefaultTasksLocalDataSource(tasksDao, reminderDao)

    @Singleton
    @Provides
    fun provideTaskRepository(localDataSource: TaskLocalDataSource): TasksRepository =
        DefaultTasksRepository(localDataSource)

    @Singleton
    @Provides
    fun provideFieldValidator(): FieldValidator = FieldValidatorDefault()

}