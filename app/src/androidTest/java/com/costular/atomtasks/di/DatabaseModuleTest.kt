package com.costular.atomtasks.di

import android.content.Context
import androidx.room.Room
import com.costular.atomtasks.db.AtomRemindersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class],
)
@Module
class DatabaseModuleTest {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AtomRemindersDatabase =
        Room.inMemoryDatabaseBuilder(context, AtomRemindersDatabase::class.java)
            .build()


}