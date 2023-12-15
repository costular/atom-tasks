package com.costular.atomtasks.tasks.helper.recurrence

import com.google.common.truth.Truth.assertThat
import java.time.LocalDate
import org.junit.Test

class DailyRecurrenceStrategyTest {

    val sut: DailyRecurrenceStrategy = DailyRecurrenceStrategy()

    @Test
    fun `Should return next 7 days no matter if it's weekday or weekend`() {
        val firstDay = LocalDate.of(2023, 12, 16)
        val expected = listOf(
            LocalDate.of(2023, 12, 17),
            LocalDate.of(2023, 12, 18),
            LocalDate.of(2023, 12, 19),
            LocalDate.of(2023, 12, 20),
            LocalDate.of(2023, 12, 21),
            LocalDate.of(2023, 12, 22),
            LocalDate.of(2023, 12, 23),
        )

        val result = sut.calculateNextOccurrences(firstDay, 7)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Should return next day`() {
        val firstDay = LocalDate.of(2023, 12, 15)
        val expected = listOf(
            LocalDate.of(2023, 12, 16),
        )

        val result = sut.calculateNextOccurrences(firstDay, 1)

        assertThat(result).isEqualTo(expected)
    }
}
