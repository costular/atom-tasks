package com.costular.atomtasks.postponetask.domain

import com.costular.core.util.PredefinedTimes
import com.costular.core.util.WeekUtil.findNextWeek
import com.costular.core.util.WeekUtil.findNextWeekend
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime

interface PostponeChoiceCalculator {
    fun calculatePostpone(postponeChoice: PostponeChoice): LocalDateTime
}

@Suppress("MagicNumber")
class DefaultPostponeChoiceCalculator(
    private val clock: Clock,
) : PostponeChoiceCalculator {
    override fun calculatePostpone(postponeChoice: PostponeChoice): LocalDateTime {
        return when (postponeChoice) {
            PostponeChoice.FifteenMinutes -> {
                now().plusMinutes(15)
            }

            PostponeChoice.OneHour -> {
                now().plusHours(1)
            }

            PostponeChoice.Tonight -> {
                val tonigth = today().atTime(PredefinedTimes.Night)
                if (tonigth.isBefore(LocalDateTime.now(clock))) {
                    tonigth.plusDays(1)
                } else {
                    tonigth
                }
            }

            PostponeChoice.TomorrowMorning -> {
                today().plusDays(1).atTime(PredefinedTimes.Morning)
            }

            PostponeChoice.NextWeekend -> {
                today().findNextWeekend().atTime(PredefinedTimes.Morning)
            }

            PostponeChoice.NextWeek -> {
                today().findNextWeek().atTime(PredefinedTimes.Morning)
            }
        }
    }

    private fun today(): LocalDate = LocalDate.now(clock)

    private fun now(): LocalDateTime = LocalDateTime.now(clock)
}
