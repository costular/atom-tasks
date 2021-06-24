package com.costular.atomhabits.di

import com.costular.atomhabits.core.net.AppDispatcherProvider
import com.costular.atomhabits.core.net.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetModule {

    @Provides
    @Singleton
    fun providesAppDispatcherProvider(): DispatcherProvider = AppDispatcherProvider()

    @AppScope
    @Provides
    @Singleton
    fun provideAppScope(dispatcher: DispatcherProvider): CoroutineScope =
        CoroutineScope(dispatcher.main + SupervisorJob())

}