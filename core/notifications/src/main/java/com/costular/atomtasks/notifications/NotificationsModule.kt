package com.costular.atomtasks.notifications

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationsModule {

    @Binds
    abstract fun bindTaskNotificationsManager(
        taskNotificationManagerImpl: TaskNotificationManagerImpl
    ): TaskNotificationManager

}
