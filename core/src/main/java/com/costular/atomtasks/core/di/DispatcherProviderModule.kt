package com.costular.atomtasks.core.di

import com.costular.atomtasks.core.net.AppDispatcherProvider
import com.costular.atomtasks.core.net.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DispatcherProviderModule {
    @Provides
    fun providesDispatcherProvider(): DispatcherProvider = AppDispatcherProvider()
}
