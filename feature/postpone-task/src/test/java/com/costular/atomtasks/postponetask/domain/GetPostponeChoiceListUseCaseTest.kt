package com.costular.atomtasks.postponetask.domain

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset


class GetPostponeChoiceListUseCaseTest {

    private lateinit var clock: Clock

    lateinit var sut: GetPostponeChoiceListUseCase

    @Before
    fun setUp() {
        clock = Clock.systemDefaultZone()
        initializeViewModel()
    }

    @Test
    fun `Should not show tonight when the time has passed when returning postpone choices`() =
        runTest {
            clock = Clock.fixed(
                LocalDateTime.of(
                    FixedDate, LocalTime.of(21, 0)
                ).toInstant(ZoneOffset.UTC),
                ZoneId.of("UTC")
            )
            initializeViewModel()

            val actual = sut.invoke(Unit)

            assertThat(actual.find { it.postponeChoiceType == PostponeChoiceType.Tonight }).isNull()
        }

    private fun initializeViewModel() {
        sut = GetPostponeChoiceListUseCase(DefaultPostponeChoiceCalculator(clock))
    }

    private companion object {
        val FixedDate = LocalDate.of(2024, 10, 13)
    }
}
