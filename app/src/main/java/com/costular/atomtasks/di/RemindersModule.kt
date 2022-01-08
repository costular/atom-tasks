package com.costular.atomtasks.di

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.costular.atomtasks.data.manager.NotifManagerImpl
import com.costular.atomtasks.data.manager.ReminderManagerImpl
import com.costular.atomtasks.domain.manager.NotifManager
import com.costular.atomtasks.domain.manager.ReminderManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemindersModule {

    @Singleton
    @Provides
    fun providesRemindManager(@ApplicationContext context: Context): ReminderManager =
        ReminderManagerImpl(context)

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @Singleton
    @Provides
    fun providesNotifManager(@ApplicationContext context: Context): NotifManager =
        NotifManagerImpl(context)

}