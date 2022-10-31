package com.costular.atomtasks.coretesting.di

import com.costular.atomtasks.coretesting.net.TestDispatcherProvider
import com.costular.core.di.DispatcherProviderModule
import com.costular.core.net.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.test.TestDispatcher

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherProviderModule::class],
)
object TestDispatcherProviderModule {
    @Provides
    fun provideTestDispatcherProvider(testDispatcher: TestDispatcher): DispatcherProvider =
        TestDispatcherProvider(testDispatcher)
}
