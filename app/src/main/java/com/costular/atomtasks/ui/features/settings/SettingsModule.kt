package com.costular.atomtasks.ui.features.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.costular.atomtasks.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SettingsModule {

    @Singleton
    @Provides
    fun provideSettingsLocalDataSource(
        dataStore: DataStore<Preferences>,
    ): SettingsLocalDataSource = SettingsLocalDataSourceImpl(dataStore)

    @Singleton
    @Provides
    fun provideSettingsRepository(
        settingsLocalDataSource: SettingsLocalDataSource,
    ): SettingsRepository = SettingsRepositoryImpl(settingsLocalDataSource)
}
