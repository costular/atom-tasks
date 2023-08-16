package com.costular.atomtasks.data.settings

import com.costular.atomtasks.data.Interactor
import javax.inject.Inject

class SetAutoforwardTasksInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : Interactor<SetAutoforwardTasksInteractor.Params>() {

    data class Params(
        val isEnabled: Boolean,
    )

    override suspend fun doWork(params: Params) {
        settingsRepository.setMoveUndoneTaskTomorrow(params.isEnabled)
    }
}
