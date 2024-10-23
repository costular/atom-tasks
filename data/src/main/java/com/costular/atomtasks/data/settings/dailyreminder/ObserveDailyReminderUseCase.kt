package com.costular.atomtasks.data.settings.dailyreminder

import com.costular.atomtasks.core.usecase.ObservableUseCase
import com.costular.atomtasks.data.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDailyReminderUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ObservableUseCase<Unit, DailyReminder> {
    override fun invoke(params: Unit): Flow<DailyReminder> {
        return settingsRepository.getDailyReminderConfiguration()
    }
}