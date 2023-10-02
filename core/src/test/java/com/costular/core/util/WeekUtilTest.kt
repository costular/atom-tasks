package com.costular.core.util

import com.costular.core.util.WeekUtil.nextWeekend
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import java.time.DayOfWeek
import java.time.LocalDate
import org.junit.Test

class WeekUtilTest {

    @Test
    fun `should show 13 nov as the last day of the week when 13 nov 2022 is passed given week starts on monday`() {
        val day = LocalDate.of(2022, 11, 13)

        val result = WeekUtil.getWeekDays(day, DayOfWeek.MONDAY)

        assertThat(result.last()).isEqualTo(day)
    }

    @Test
    fun `should show 7 nov as the first day of the week when 13 nov 2022 is passed given week starts on monday`() {
        val day = LocalDate.of(2022, 11, 13)
        val expected = LocalDate.of(2022, 11, 7)

        val result = WeekUtil.getWeekDays(day, DayOfWeek.MONDAY)

        assertThat(result.first()).isEqualTo(expected)
    }

    @Test
    fun `should show 13 nov as the first day of the week when 13 nov 2022 is passed given week starts on sunday`() {
        val day = LocalDate.of(2022, 11, 13)

        val result = WeekUtil.getWeekDays(day, DayOfWeek.SUNDAY)

        assertThat(result.first()).isEqualTo(day)
    }

    @Test
    fun `should show 16 nov as the last day of the week when 13 nov 2022 is passed given week starts on sunday`() {
        val day = LocalDate.of(2022, 11, 13)
        val expected = LocalDate.of(2022, 11, 19)

        val result = WeekUtil.getWeekDays(day, DayOfWeek.SUNDAY)

        assertThat(result.last()).isEqualTo(expected)
    }

    @Test
    fun `Should return 2023-10-07 (Saturday) when nextWeekend called given date is 2023-10-02 (Monday)`() {
        val date = LocalDate.of(2023, 10, 2)
        val nextWeekend = date.nextWeekend()
        assertThat(nextWeekend).isEqualTo(LocalDate.of(2023, 10, 7))
    }

    @Test
    fun `Should return 2023-10-07 (Saturday) when nextWeekend called given date is 2023-10-06 (Friday)`() {
        val date = LocalDate.of(2023, 10, 6)
        val nextWeekend = date.nextWeekend()
        assertThat(nextWeekend).isEqualTo(LocalDate.of(2023, 10, 7))
    }

    @Test
    fun `Should return 2023-10-08 (Sunday) when nextWeekend called given date is 2023-10-07 (Saturday)`() {
        val date = LocalDate.of(2023, 10, 7)
        val nextWeekend = date.nextWeekend()
        assertThat(nextWeekend).isEqualTo(LocalDate.of(2023, 10, 8))
    }

    @Test
    fun `Should return 2023-10-14 (Saturday) when nextWeekend called given date is 2023-10-08 (Sunday)`() {
        val date = LocalDate.of(2023, 10, 8)
        val nextWeekend = date.nextWeekend()
        assertThat(nextWeekend).isEqualTo(LocalDate.of(2023, 10, 14))
    }
}
