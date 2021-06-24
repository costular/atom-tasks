package com.costular.atomhabits.di

import android.content.Context
import androidx.room.Room
import com.costular.atomhabits.data.db.AtomHabitDatabase
import com.costular.atomhabits.data.habits.*
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
            .build()

    @Singleton
    @Provides
    fun provideHabitsDao(db: AtomHabitDatabase): HabitsDao = db.getHabitsDao()

    @Singleton
    @Provides
    fun providesHabitLocalDataSource(habitsDao: HabitsDao): HabitLocalDataSource =
        DefaultHabitLocalDataSource(habitsDao)

    @Singleton
    @Provides
    fun provideHabitsRepository(localDataSource: HabitLocalDataSource): HabitsRepository =
        DefaultHabitsRepository(localDataSource)

}