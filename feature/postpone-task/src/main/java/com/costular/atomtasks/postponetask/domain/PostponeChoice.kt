package com.costular.atomtasks.postponetask.domain

sealed interface PostponeChoice {

    data object FifteenMinutes : PostponeChoice

    data object OneHour : PostponeChoice

    data object Tonight : PostponeChoice

    data object TomorrowMorning : PostponeChoice

    data object NextWeekend : PostponeChoice

    data object NextWeek : PostponeChoice
}
