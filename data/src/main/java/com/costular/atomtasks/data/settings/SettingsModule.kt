package com.costular.atomtasks.data.settings

import com.costular.atomtasks.data.settings.dailyreminder.DailyReminderAlarmScheduler
import com.costular.atomtasks.data.settings.dailyreminder.DailyReminderAlarmSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface SettingsModule {
    @Binds
    fun bindsSettingsLocalDataSource(
        impl: SettingsLocalDataSourceImpl,
    ): SettingsLocalDataSource

    @Binds
    fun bindsSettingsRepository(
        impl: SettingsRepositoryImpl,
    ): SettingsRepository

    @Binds
    fun bindsDailyReminderAlarmScheduler(
        impl: DailyReminderAlarmSchedulerImpl,
    ): DailyReminderAlarmScheduler
}
