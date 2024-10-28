package com.costular.atomtasks.data.settings.dailyreminder

import com.costular.atomtasks.core.usecase.EmptyParams
import com.costular.atomtasks.core.usecase.UseCase
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class SyncDailyReminderUseCase @Inject constructor(
    private val observeDailyReminderUseCase: ObserveDailyReminderUseCase,
    private val scheduler: DailyReminderAlarmScheduler,
) : UseCase<Unit, Unit> {
    override suspend fun invoke(params: Unit) {
        val dailyReminder = observeDailyReminderUseCase(EmptyParams).take(1).toList().first()

        if (dailyReminder.isEnabled && dailyReminder.time != null) {
            val configuredTime = dailyReminder.time
            val dateTime = findClosestDateTime(configuredTime)
            scheduler.schedule(dateTime)
        } else {
            scheduler.remove()
        }
    }

    private fun findClosestDateTime(configuredTime: LocalTime): LocalDateTime {
        val now = LocalTime.now()
        return if (now.isBefore(configuredTime)) {
            LocalDate.now().atTime(configuredTime)
        } else {
            LocalDate.now().plusDays(1).atTime(configuredTime)
        }
    }
}
