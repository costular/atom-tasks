package com.costular.atomtasks.di

import com.costular.atomtasks.data.manager.ErrorLoggerImpl
import com.costular.atomtasks.domain.manager.ErrorLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ErrorLoggerModule {

    @Singleton
    @Binds
    abstract fun provideErrorLogger(errorLoggerImpl: ErrorLoggerImpl): ErrorLogger
}
