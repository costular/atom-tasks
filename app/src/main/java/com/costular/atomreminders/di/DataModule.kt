package com.costular.atomreminders.di

import android.content.Context
import androidx.room.Room
import com.costular.atomreminders.db.AtomHabitDatabase
import com.costular.atomreminders.data.habitrecord.HabitRecordDao
import com.costular.atomreminders.data.habits.*
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
    fun provideDatabase(@ApplicationContext context: Context): AtomHabitDatabase =
        Room.databaseBuilder(context, AtomHabitDatabase::class.java, "atomhabits.db")
            .fallbackToDestructiveMigration() // TODO: 26/6/21 remove this
            .build()

    @Singleton
    @Provides
    fun provideHabitsDao(db: AtomHabitDatabase): HabitsDao = db.getHabitsDao()

    @Singleton
    @Provides
    fun providesReminderDao(db: AtomHabitDatabase): ReminderDao = db.getRemindersDao()

    @Singleton
    @Provides
    fun provideHabitRecordDao(db: AtomHabitDatabase): HabitRecordDao = db.getHabitRecordDao()

    @Singleton
    @Provides
    fun providesHabitLocalDataSource(
        habitsDao: HabitsDao,
        reminderDao: ReminderDao,
        habitRecordDao: HabitRecordDao
    ): HabitLocalDataSource =
        DefaultHabitLocalDataSource(habitsDao, reminderDao, habitRecordDao)

    @Singleton
    @Provides
    fun provideHabitsRepository(localDataSource: HabitLocalDataSource): HabitsRepository =
        DefaultHabitsRepository(localDataSource)

}