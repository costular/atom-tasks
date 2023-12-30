package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.core.usecase.UseCase
import javax.inject.Inject

class AreExactRemindersAvailable @Inject constructor(
    private val tasksReminderManager: TaskReminderManager,
) : UseCase<Unit, Boolean> {

    override suspend fun invoke(params: Unit): Boolean =
        tasksReminderManager.canScheduleReminders()
}
