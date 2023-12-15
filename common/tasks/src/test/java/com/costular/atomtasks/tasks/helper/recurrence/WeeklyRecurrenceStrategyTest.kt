package com.costular.atomtasks.tasks.helper.recurrence

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import java.time.LocalDate
import org.junit.Test

class WeeklyRecurrenceStrategyTest {

    val sut: WeeklyRecurrenceStrategy = WeeklyRecurrenceStrategy()

    @Test
    fun `Should return next week`() {
        val today = LocalDate.of(2023, 12, 15)
        val expected = listOf(
            LocalDate.of(2023, 12, 22)
        )

        val result = sut.calculateNextOccurrences(today, 1)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Should return next weeks given 3 next occurrences`() {
        val today = LocalDate.of(2023, 12, 15)
        val expected = listOf(
            LocalDate.of(2023, 12, 22),
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2024, 1, 5),
        )

        val result = sut.calculateNextOccurrences(today, 3)

        assertThat(result).isEqualTo(expected)
    }
}
