package com.costular.atomtasks.di

import com.costular.atomtasks.data.manager.ErrorLoggerImpl
import com.costular.atomtasks.domain.manager.ErrorLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ErrorLoggerModule {

    @Singleton
    @Provides
    fun provideErrorLogger(): ErrorLogger = ErrorLoggerImpl()
}
