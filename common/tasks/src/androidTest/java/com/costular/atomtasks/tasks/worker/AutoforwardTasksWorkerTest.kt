package com.costular.atomtasks.tasks.worker

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.google.common.truth.Truth
import java.time.Duration
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AutoforwardTasksWorkerTest {

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun testWithInitialDelay() {
        val request = AutoforwardTasksWorker.setUp(Duration.ofMinutes(45))

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val workManager = WorkManager.getInstance(context)
        val testDriver =
            WorkManagerTestInitHelper.getTestDriver(context)

        workManager.enqueue(request).result.get()
        testDriver?.setInitialDelayMet(request.id)
        val workInfo = workManager.getWorkInfoById(request.id).get()

        Truth.assertThat(workInfo.state).isInstanceOf(WorkInfo.State.SUCCEEDED::class.java)
    }
}
