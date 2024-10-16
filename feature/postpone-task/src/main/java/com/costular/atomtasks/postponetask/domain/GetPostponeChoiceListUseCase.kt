package com.costular.atomtasks.postponetask.domain

import com.costular.atomtasks.core.usecase.UseCase
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class GetPostponeChoiceListUseCase @Inject constructor(
    private val postponeChoiceCalculator: PostponeChoiceCalculator,
) : UseCase<Unit, List<PostponeChoice>> {

    override suspend fun invoke(params: Unit): List<PostponeChoice> {
        return Choices.map { PostponeChoice(it, postponeChoiceCalculator.calculatePostpone(it)) }
            .filter {
                it.postponeChoiceType != PostponeChoiceType.Tonight ||
                        (it.postponeChoiceType == PostponeChoiceType.Tonight &&
                                it.postponeDateTime?.toLocalDate() == LocalDate.now())
            }
    }

    companion object {
        internal val Choices = listOf(
            PostponeChoiceType.ThirtyMinutes,
            PostponeChoiceType.OneHour,
            PostponeChoiceType.ThreeHours,
            PostponeChoiceType.Tonight,
            PostponeChoiceType.TomorrowMorning,
            PostponeChoiceType.NextWeek,
            PostponeChoiceType.Custom,
        )
    }
}
