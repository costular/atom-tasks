package com.costular.atomtasks.tasks.helper.recurrence

import com.google.common.truth.Truth
import java.time.LocalDate
import org.junit.Test

class YearlyRecurrenceStrategyTest {

    private val sut = YearlyRecurrenceStrategy()

    @Test
    fun `Should return next year`() {
        val today = LocalDate.of(2023, 12, 15)
        val expected = listOf(
            LocalDate.of(2024, 12, 15)
        )

        val result = sut.calculateNextOccurrences(today, 1)

        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Should return next years given 3 next occurrences`() {
        val today = LocalDate.of(2023, 1, 15)
        val expected = listOf(
            LocalDate.of(2024, 1, 15),
            LocalDate.of(2025, 1, 15),
            LocalDate.of(2026, 1, 15),
        )

        val result = sut.calculateNextOccurrences(today, 3)

        Truth.assertThat(result).isEqualTo(expected)
    }
}
