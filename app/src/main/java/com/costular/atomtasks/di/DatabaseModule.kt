package com.costular.atomtasks.di

import android.content.Context
import androidx.room.Room
import com.costular.atomtasks.db.AtomRemindersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AtomRemindersDatabase =
        Room.databaseBuilder(context, AtomRemindersDatabase::class.java, "atomtasks.db")
            .build()

}