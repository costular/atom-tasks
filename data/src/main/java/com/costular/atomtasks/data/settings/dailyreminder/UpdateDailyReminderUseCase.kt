package com.costular.atomtasks.data.settings.dailyreminder

import com.costular.atomtasks.core.usecase.EmptyParams
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.data.settings.SettingsRepository
import java.time.LocalTime
import javax.inject.Inject

class UpdateDailyReminderUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val syncDailyReminderUseCase: SyncDailyReminderUseCase,
) : UseCase<UpdateDailyReminderUseCase.Params, Unit> {

    data class Params(
        val isEnabled: Boolean,
        val time: LocalTime,
    )

    override suspend fun invoke(params: Params) {
        settingsRepository.updateDailyReminder(params.isEnabled, params.time)
        syncDailyReminderUseCase.invoke(EmptyParams)
    }
}