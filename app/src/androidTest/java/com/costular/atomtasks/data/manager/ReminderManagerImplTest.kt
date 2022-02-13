package com.costular.atomtasks.data.manager

import android.app.PendingIntent
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import com.costular.atomtasks.data.receiver.NotifyTaskReceiver
import com.costular.atomtasks.domain.manager.ReminderManager
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class ReminderManagerImplTest {

    lateinit var reminderManager: ReminderManager

    @Before
    fun setUp() {
        reminderManager = ReminderManagerImpl(
            InstrumentationRegistry.getInstrumentation().targetContext
        )
    }

    @After
    fun tearDown() {
        reminderManager.cancel(TASK_ID)
    }

    @Test
    fun testAlarmIsSet() {
        reminderManager.set(TASK_ID, LocalDateTime.now())

        assertThat(hasPendingIntent()).isTrue()
    }

    @Test
    fun testAlarmIsCancelled() {
        reminderManager.set(TASK_ID, LocalDateTime.now())
        reminderManager.cancel(TASK_ID)

        assertThat(hasPendingIntent()).isFalse()
    }

    private fun hasPendingIntent() =
        PendingIntent.getBroadcast(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TASK_ID.toInt(),
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                NotifyTaskReceiver::class.java
            ),
            PendingIntent.FLAG_NO_CREATE
        ) != null

    companion object {
        const val TASK_ID = 100L
    }
}
