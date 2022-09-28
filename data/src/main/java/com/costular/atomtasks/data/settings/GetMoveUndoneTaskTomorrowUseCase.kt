package com.costular.atomtasks.data.settings

import com.costular.atomtasks.data.SubjectInteractor
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMoveUndoneTaskTomorrowUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : SubjectInteractor<Unit, Boolean>() {

    override fun createObservable(params: Unit): Flow<Boolean> {
        return settingsRepository.observeMoveUndoneTaskTomorrow()
    }

}
