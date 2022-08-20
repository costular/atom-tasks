package com.costular.core.di

import com.costular.core.net.AppDispatcherProvider
import com.costular.core.net.DispatcherProvider
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
