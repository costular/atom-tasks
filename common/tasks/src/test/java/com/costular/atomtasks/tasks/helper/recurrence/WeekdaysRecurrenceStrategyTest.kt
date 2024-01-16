package com.costular.atomtasks.tasks.helper.recurrence

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate

class WeekdaysRecurrenceStrategyTest {

    val sut: WeekdaysRecurrenceStrategy = WeekdaysRecurrenceStrategy()

    @Test
    fun `Should return next weekday Monday 18 Dec given that start day is Fri 15 Dec`() {
        val today = LocalDate.of(2023, 12, 15)
        val expected = listOf(
            LocalDate.of(2023, 12, 18)
        )

        val result = sut.calculateNextOccurrences(today, 1)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Should return next 5 weekdays given that start day is Wed 13 Dec`() {
        val today = LocalDate.of(2023, 12, 13)
        val expected = listOf(
            LocalDate.of(2023, 12, 14),
            LocalDate.of(2023, 12, 15),
            LocalDate.of(2023, 12, 18),
            LocalDate.of(2023, 12, 19),
            LocalDate.of(2023, 12, 20),
        )

        val result = sut.calculateNextOccurrences(today, 5)

        assertThat(result).isEqualTo(expected)
    }
}
