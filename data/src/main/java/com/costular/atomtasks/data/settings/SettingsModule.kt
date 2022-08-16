package com.costular.atomtasks.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class SettingsModule {

    @Provides
    fun provideSettingsLocalDataSource(
        dataStore: DataStore<Preferences>,
    ): SettingsLocalDataSource = SettingsLocalDataSourceImpl(dataStore)

    @Provides
    fun provideSettingsRepository(
        settingsLocalDataSource: SettingsLocalDataSource,
    ): SettingsRepository = SettingsRepositoryImpl(settingsLocalDataSource)
}
