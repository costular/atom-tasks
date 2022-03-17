package com.costular.atomtasks.ui.features.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.costular.atomtasks.domain.model.Theme
import com.google.common.truth.Truth.assertThat
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class SettingsLocalDataSourceImplTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testCoroutineDispatcher + Job())
    private val testContext: Context = ApplicationProvider.getApplicationContext()

    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { testContext.preferencesDataStoreFile("TEST_DATASTORE") },
        )

    lateinit var sut: SettingsLocalDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        sut = SettingsLocalDataSourceImpl(testDataStore)
    }

    @Test
    fun shouldReturnSystemTheme_whenGetTheme_givenDefaultPreferences() = testScope.runBlockingTest {
        sut.observeTheme().test {
            assertThat(awaitItem()).isEqualTo(Theme.SYSTEM)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldReturnLightTheme_whenSetThemeToLight() = testScope.runBlockingTest {
        val theme = Theme.LIGHT

        sut.setTheme(theme)

        sut.observeTheme().test {
            assertThat(awaitItem()).isEqualTo(theme)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        testScope.runBlockingTest { testDataStore.edit { it.clear() } }
        testScope.cancel()
    }
}
