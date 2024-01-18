package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.core.usecase.invoke
import com.costular.atomtasks.data.settings.IsAutoforwardTasksSettingEnabledUseCase
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class AutoforwardTasksUseCase @Inject constructor(
    private val isAutoforwardTasksSettingEnabledUseCase: IsAutoforwardTasksSettingEnabledUseCase,
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val editTaskUseCase: EditTaskUseCase,
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
            ?.tap { tasks ->
                tasks.filter { !it.isDone }
                    .forEach { task ->
                        editTaskUseCase(
                            EditTaskUseCase.Params(
                                taskId = task.id,
                                name = task.name,
                                date = task.day.plusDays(1),
                                reminderTime = task.reminder?.time,
                                recurrenceType = task.recurrenceType,
                            ),
                        )
                    }
            }
    }
}
