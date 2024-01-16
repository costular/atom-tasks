package com.costular.atomtasks.data.settings

import com.costular.atomtasks.core.usecase.ObservableUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class IsAutoforwardTasksSettingEnabledUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ObservableUseCase<Unit, Boolean> {
    override fun invoke(params: Unit): Flow<Boolean> =
        settingsRepository.observeMoveUndoneTaskTomorrow()
}
