package com.costular.core.util

import com.costular.atomtasks.core.util.WeekUtil
import com.costular.atomtasks.core.util.WeekUtil.findNextWeek
import com.costular.atomtasks.core.util.WeekUtil.findNextWeekend
import com.google.common.truth.Truth.assertThat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale
import org.junit.Test

class WeekUtilTest {

    @Test
    fun `Should return 2023-10-07 (Saturday) when nextWeekend called given date is 2023-10-02 (Monday)`() {
        givenWeekStartsOnMonday()

        val date = LocalDate.of(2023, 10, 2)
        val nextWeekend = date.findNextWeekend()
        assertThat(nextWeekend).isEqualTo(LocalDate.of(2023, 10, 7))
    }

    @Test
    fun `Should return 2023-10-07 (Saturday) when nextWeekend called given date is 2023-10-06 (Friday)`() {
        givenWeekStartsOnMonday()

        val date = LocalDate.of(2023, 10, 6)
        val nextWeekend = date.findNextWeekend()
        assertThat(nextWeekend).isEqualTo(LocalDate.of(2023, 10, 7))
    }

    @Test
    fun `Should return 2023-10-08 (Sunday) when nextWeekend called given date is 2023-10-07 (Saturday)`() {
        givenWeekStartsOnMonday()

        val date = LocalDate.of(2023, 10, 7)
        val nextWeekend = date.findNextWeekend()
        assertThat(nextWeekend).isEqualTo(LocalDate.of(2023, 10, 8))
    }

    @Test
    fun `Should return 2023-10-14 (Saturday) when nextWeekend called given date is 2023-10-08 (Sunday)`() {
        givenWeekStartsOnMonday()

        val date = LocalDate.of(2023, 10, 8)
        val nextWeekend = date.findNextWeekend()
        assertThat(nextWeekend).isEqualTo(LocalDate.of(2023, 10, 14))
    }

    @Test
    fun `Should return next first day of the week when findNextWeek called given date is a Monday`() {
        givenWeekStartsOnMonday()

        val today = LocalDate.of(2023, 10, 2)
        val date = today.findNextWeek()
        val expectedDate = LocalDate.of(2023, 10, 9)
        assertThat(date).isEqualTo(expectedDate)
    }

    @Test
    fun `Should return next first day of the week when findNextWeek called given date is a Friday`() {
        givenWeekStartsOnMonday()

        val date = LocalDate.of(2023, 10, 6)
        val nextWeekStart = date.findNextWeek()
        val expectedDate = LocalDate.of(2023, 10, 9)
        assertThat(nextWeekStart).isEqualTo(expectedDate)
    }

    @Test
    fun `Should return next Sunday when findNextWeek called given date is a Monday and locale is US`() {
        givenWeekStartsOnSunday()

        val date = LocalDate.of(2023, 10, 2)
        val nextWeekStart = date.findNextWeek()
        val expectedDate = date.plusDays(6)
        assertThat(nextWeekStart).isEqualTo(expectedDate)
    }

    @Test
    fun `Should return next Sunday when findNextWeek called given date is a Saturday and locale is US`() {
        givenWeekStartsOnSunday()

        val date = LocalDate.of(2023, 10, 7)
        val nextWeekStart = date.findNextWeek()
        val expectedDate = date.plusDays(1)
        assertThat(nextWeekStart).isEqualTo(expectedDate)
    }

    @Test
    fun `Should return next Sunday when findNextWeek called given date is a Sunday and locale is US`() {
        givenWeekStartsOnSunday()

        val date = LocalDate.of(2023, 10, 8)
        val nextWeekStart = date.findNextWeek()
        val expectedDate = date.plusWeeks(1)
        assertThat(nextWeekStart).isEqualTo(expectedDate)
    }

    private fun givenWeekStartsOnSunday() {
        Locale.setDefault(Locale.US)
    }

    private fun givenWeekStartsOnMonday() {
        Locale.setDefault(Locale.UK)
    }
}
