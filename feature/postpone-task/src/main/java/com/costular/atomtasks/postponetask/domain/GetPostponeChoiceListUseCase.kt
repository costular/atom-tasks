package com.costular.atomtasks.postponetask.domain

import com.costular.atomtasks.core.usecase.UseCase
import javax.inject.Inject

class GetPostponeChoiceListUseCase @Inject constructor(): UseCase<Unit, List<PostponeChoice>> {
    override suspend fun invoke(params: Unit): List<PostponeChoice> = listOf(
        PostponeChoice.FifteenMinutes,
        PostponeChoice.OneHour,
        PostponeChoice.Tonight,
        PostponeChoice.TomorrowMorning,
        PostponeChoice.NextWeek,
    )
}
