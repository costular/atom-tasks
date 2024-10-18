package com.costular.atomtasks.postponetask.domain

sealed interface PostponeChoiceType {

    data object ThirtyMinutes : PostponeChoiceType

    data object OneHour : PostponeChoiceType

    data object ThreeHours: PostponeChoiceType

    data object Tonight : PostponeChoiceType

    data object TomorrowMorning : PostponeChoiceType

    data object NextWeekend : PostponeChoiceType

    data object NextWeek : PostponeChoiceType

    data object Custom : PostponeChoiceType
}
