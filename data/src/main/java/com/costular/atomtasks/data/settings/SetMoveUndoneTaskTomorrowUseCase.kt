package com.costular.atomtasks.data.settings

import com.costular.atomtasks.data.Interactor
import javax.inject.Inject

class SetMoveUndoneTaskTomorrowUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
): Interactor<SetMoveUndoneTaskTomorrowUseCase.Params>() {

    data class Params(
        val isEnabled: Boolean
    )

    override suspend fun doWork(params: Params) {
        settingsRepository.setMoveUndoneTaskTomorrow(params.isEnabled)
    }

}
