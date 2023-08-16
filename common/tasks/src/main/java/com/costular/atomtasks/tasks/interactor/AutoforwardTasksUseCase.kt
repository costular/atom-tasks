package com.costular.atomtasks.tasks.interactor

import android.util.Log
import com.costular.atomtasks.data.settings.IsAutoforwardTasksSettingEnabledUseCase
import com.costular.atomtasks.tasks.ObserveTasksUseCase
import com.costular.core.usecase.UseCase
import com.costular.core.usecase.invoke
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class AutoforwardTasksUseCase @Inject constructor(
    private val isAutoforwardTasksSettingEnabledUseCase: IsAutoforwardTasksSettingEnabledUseCase,
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : UseCase<AutoforwardTasksUseCase.Params, Unit> {

    data class Params(
        val today: LocalDate,
    )

    override suspend fun invoke(params: Params) {
        val isAutoforwardTasksEnabled = isAutoforwardTasksSettingEnabledUseCase().first()

        if (!isAutoforwardTasksEnabled) {
            return
        }

        observeTasksUseCase(ObserveTasksUseCase.Params(day = params.today.minusDays(1)))
            .firstOrNull()
            ?.filter { !it.isDone }
            ?.forEach { task ->
                updateTaskUseCase(
                    UpdateTaskUseCase.Params(
                        taskId = task.id,
                        name = task.name,
                        date = task.day.plusDays(1),
                        reminderEnabled = task.reminder?.isEnabled ?: false,
                        reminderTime = task.reminder?.time,
                    ),
                )
            }
    }
}
