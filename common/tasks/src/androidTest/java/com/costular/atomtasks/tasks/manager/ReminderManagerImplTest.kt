package com.costular.atomtasks.tasks.manager

import android.app.PendingIntent
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.tasks.helper.TaskReminderManagerImpl
import com.google.common.truth.Truth.assertThat
import java.time.LocalDateTime
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore("For some reason WorkManager is failing while running these test on GitHub Actions")
class ReminderManagerImplTest {

    lateinit var reminderManager: TaskReminderManager

    @Before
    fun setUp() {
        reminderManager = TaskReminderManagerImpl(
            InstrumentationRegistry.getInstrumentation().targetContext,
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
                com.costular.atomtasks.tasks.receiver.NotifyTaskReceiver::class.java,
            ),
            PendingIntent.FLAG_NO_CREATE,
        ) != null

    companion object {
        const val TASK_ID = 100L
    }
}
