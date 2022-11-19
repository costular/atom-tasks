package com.costular.core.util

import com.google.common.truth.Truth
import java.time.DayOfWeek
import java.time.LocalDate
import org.junit.Test

class WeekUtilTest {

    @Test
    fun `should show 13 nov as the last day of the week when 13 nov 2022 is passed given week starts on monday`() {
        val day = LocalDate.of(2022, 11, 13)

        val result = WeekUtil.getWeekDays(day, DayOfWeek.MONDAY)

        Truth.assertThat(result.last()).isEqualTo(day)
    }

    @Test
    fun `should show 7 nov as the first day of the week when 13 nov 2022 is passed given week starts on monday`() {
        val day = LocalDate.of(2022, 11, 13)
        val expected = LocalDate.of(2022, 11, 7)

        val result = WeekUtil.getWeekDays(day, DayOfWeek.MONDAY)

        Truth.assertThat(result.first()).isEqualTo(expected)
    }

    @Test
    fun `should show 13 nov as the first day of the week when 13 nov 2022 is passed given week starts on sunday`() {
        val day = LocalDate.of(2022, 11, 13)

        val result = WeekUtil.getWeekDays(day, DayOfWeek.SUNDAY)

        Truth.assertThat(result.first()).isEqualTo(day)
    }

    @Test
    fun `should show 16 nov as the last day of the week when 13 nov 2022 is passed given week starts on sunday`() {
        val day = LocalDate.of(2022, 11, 13)
        val expected = LocalDate.of(2022, 11, 19)

        val result = WeekUtil.getWeekDays(day, DayOfWeek.SUNDAY)

        Truth.assertThat(result.last()).isEqualTo(expected)
    }
}
