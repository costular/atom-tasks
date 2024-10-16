package com.costular.atomtasks.postponetask.domain

import com.costular.atomtasks.core.util.PredefinedTimes
import com.costular.atomtasks.core.util.WeekUtil.findNextWeek
import com.costular.atomtasks.core.util.WeekUtil.findNextWeekend
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime

interface PostponeChoiceCalculator {
    fun calculatePostpone(postponeChoiceType: PostponeChoiceType): LocalDateTime?
}

@Suppress("MagicNumber")
class DefaultPostponeChoiceCalculator(
    private val clock: Clock,
) : PostponeChoiceCalculator {
    override fun calculatePostpone(postponeChoiceType: PostponeChoiceType): LocalDateTime? {
        return when (postponeChoiceType) {
            PostponeChoiceType.ThirtyMinutes -> {
                now().plusMinutes(30)
            }

            PostponeChoiceType.OneHour -> {
                now().plusHours(1)
            }

            PostponeChoiceType.ThreeHours -> {
                now().plusHours(3)
            }

            PostponeChoiceType.Tonight -> {
                val tonigth = today().atTime(PredefinedTimes.Night)
                if (tonigth.isBefore(LocalDateTime.now(clock))) {
                    tonigth.plusDays(1)
                } else {
                    tonigth
                }
            }

            PostponeChoiceType.TomorrowMorning -> {
                today().plusDays(1).atTime(PredefinedTimes.Morning)
            }

            PostponeChoiceType.NextWeekend -> {
                today().findNextWeekend().atTime(PredefinedTimes.Morning)
            }

            PostponeChoiceType.NextWeek -> {
                today().findNextWeek().atTime(PredefinedTimes.Morning)
            }

            PostponeChoiceType.Custom -> {
                null
            }
        }
    }

    private fun today(): LocalDate = LocalDate.now(clock)

    private fun now(): LocalDateTime = LocalDateTime.now(clock)
}
