package com.costular.atomtasks.feature.onboarding

import com.costular.atomtasks.core.locale.LocaleResolver
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.repository.TasksRepository
import com.costular.atomtasks.tasks.usecase.CreateTaskUseCase
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class PrepopulateOnboardingTasksUseCase @Inject constructor(
    private val localeResolver: LocaleResolver,
    private val tasksRepository: TasksRepository,
    private val createTaskUseCase: CreateTaskUseCase,
) : UseCase<Unit, Unit> {
    override suspend fun invoke(params: Unit) {
        if (tasksRepository.getTaskCount() > 0) {
            return
        }

        val locale = localeResolver.getLocale()
        val getTasksByLocale = PrepopulateTasks[locale.language.uppercase()]
        getTasksByLocale?.forEach { task ->
            createTaskUseCase.invoke(task.asParams())
        }
    }

    private fun PrepopulateTask.asParams(): CreateTaskUseCase.Params = CreateTaskUseCase.Params(
        name = name,
        date = date,
        reminderEnabled = true,
        reminderTime = reminder,
        recurrenceType = recurrence,
    )

    private companion object {
        val PrepopulateTasks: Map<String, List<PrepopulateTask>> = mapOf(
            "EN" to listOf(
                PrepopulateTask(
                    name = "Yoga \uD83E\uDDD8",
                    date = LocalDate.now(),
                    reminder = LocalTime.of(8, 0),
                    recurrence = RecurrenceType.WEEKLY,
                ),
                PrepopulateTask(
                    name = "Work out \uD83C\uDFCB\uFE0F",
                    date = LocalDate.now(),
                    reminder = null,
                    recurrence = RecurrenceType.DAILY,
                ),
                PrepopulateTask(
                    name = "Dinner with friends",
                    date = LocalDate.now(),
                    reminder = LocalTime.of(7, 0),
                    recurrence = null,
                )
            ),
            "ES" to listOf(
                PrepopulateTask(
                    name = "Yoga \uD83E\uDDD8",
                    date = LocalDate.now(),
                    reminder = LocalTime.of(9, 0),
                    recurrence = RecurrenceType.WEEKLY,
                ),
                PrepopulateTask(
                    name = "Entreno \uD83C\uDFCB\uFE0F",
                    date = LocalDate.now(),
                    reminder = null,
                    recurrence = RecurrenceType.DAILY,
                ),
                PrepopulateTask(
                    name = "Cena con amigos",
                    date = LocalDate.now(),
                    reminder = LocalTime.of(9, 0),
                    recurrence = null,
                )
            )
        )
    }
}