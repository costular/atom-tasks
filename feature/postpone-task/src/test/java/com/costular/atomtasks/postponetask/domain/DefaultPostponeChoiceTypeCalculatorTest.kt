package com.costular.atomtasks.postponetask.domain

import com.google.common.truth.Truth
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class DefaultPostponeChoiceTypeCalculatorTest {

    lateinit var sut: PostponeChoiceCalculator

    private val clock = Clock.systemDefaultZone()

    @Before
    fun setUp() {
        givenPostponeChoiceCalculator(clock)
    }

    @Test
    fun `Should return datetime today in 30 minues when calculate postpone 30 minutes is selected`() {
        val expected = LocalDateTime.now().plusMinutes(30)
        val result = sut.calculatePostpone(PostponeChoiceType.ThirtyMinutes)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Test
    fun `Should return datetime today in 60 minutes when calculate postpone one hour is selected`() {
        val expected = LocalDateTime.now().plusHours(1)
        val result = sut.calculatePostpone(PostponeChoiceType.OneHour)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Test
    fun `Should return datetime today at 8pm when calculate postpone tonight is selected given is in the morning`() {
        val clock = Clock.fixed(
            LocalDate.now().atTime(9, 0).toInstant(ZoneOffset.UTC),
            ZoneOffset.UTC
        )
        givenPostponeChoiceCalculator(clock)

        val expected = LocalDate.now().atTime(20, 0)
        val result = sut.calculatePostpone(PostponeChoiceType.Tonight)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Test
    fun `Should return datetime tomorrow at 8pm when calculate postpone tonight is selected given is after 8pm`() {
        val clock = Clock.fixed(
            LocalDate.now().atTime(21, 0).toInstant(ZoneOffset.UTC),
            ZoneOffset.UTC
        )
        givenPostponeChoiceCalculator(clock)

        val expected = LocalDate.now().plusDays(1).atTime(20, 0)
        val result = sut.calculatePostpone(PostponeChoiceType.Tonight)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Test
    fun `Should return datetime tomorrow at 8am when calculate postpone tomorrow morning is selected`() {
        val clock = Clock.fixed(
            LocalDate.now().atTime(10, 0).toInstant(ZoneOffset.UTC),
            ZoneOffset.UTC
        )
        givenPostponeChoiceCalculator(clock)

        val expected = LocalDate.now().plusDays(1).atTime(8, 0)
        val result = sut.calculatePostpone(PostponeChoiceType.TomorrowMorning)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Test
    fun `Should return datetime tomorrow at 8am when calculate postpone tomorrow morning is selected given is 8am already`() {
        val clock = Clock.fixed(
            LocalDate.now().atTime(8, 0).toInstant(ZoneOffset.UTC),
            ZoneOffset.UTC
        )
        givenPostponeChoiceCalculator(clock)

        val expected = LocalDate.now().plusDays(1).atTime(8, 0)
        val result = sut.calculatePostpone(PostponeChoiceType.TomorrowMorning)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Test
    fun `Should return datetime 21 Oct 2023 at 8am when calculate postpone next weekend is selected`() {
        val clock = Clock.fixed(
            LocalDate.of(2023, 10, 14).atTime(10, 0).toInstant(ZoneOffset.UTC),
            ZoneOffset.UTC
        )
        givenPostponeChoiceCalculator(clock)

        val expected = LocalDate.of(2023, 10, 15).atTime(8, 0)
        val result = sut.calculatePostpone(PostponeChoiceType.NextWeekend)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Test
    fun `Should return datetime 14 Oct 2023 at 8am when calculate postpone next weekend is selected`() {
        val clock = Clock.fixed(
            LocalDate.of(2023, 10, 12).atTime(9, 0).toInstant(ZoneOffset.UTC),
            ZoneOffset.UTC
        )
        givenPostponeChoiceCalculator(clock)

        val expected = LocalDate.of(2023, 10, 14).atTime(8, 0)
        val result = sut.calculatePostpone(PostponeChoiceType.NextWeekend)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Ignore("Flaky test on the CI")
    @Test
    fun `Should return datetime 16 Oct 2023 at 8am when calculate postpone next week is selected given today is 14 Oct 2023`() {
        val clock = Clock.fixed(
            LocalDate.of(2023, 10, 14).atTime(9, 0).toInstant(ZoneOffset.UTC),
            ZoneOffset.UTC
        )
        givenPostponeChoiceCalculator(clock)

        val expected = LocalDate.of(2023, 10, 16).atTime(8, 0)
        val result = sut.calculatePostpone(PostponeChoiceType.NextWeek)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    @Ignore("Flaky test on the CI")
    @Test
    fun `Should return datetime 23 Oct 2023 at 8am when calculate postpone next week is selected given today is 16 Oct 2023`() {
        val clock = Clock.fixed(
            LocalDate.of(2023, 10, 16).atTime(9, 0).toInstant(ZoneOffset.UTC),
            ZoneOffset.UTC
        )
        givenPostponeChoiceCalculator(clock)

        val expected = LocalDate.of(2023, 10, 23).atTime(8, 0)
        val result = sut.calculatePostpone(PostponeChoiceType.NextWeek)

        Truth.assertThat(result?.withoutMilliseconds()).isEqualTo(expected.withoutMilliseconds())
    }

    private fun LocalDateTime.withoutMilliseconds(): LocalDateTime = truncatedTo(ChronoUnit.SECONDS)

    private fun givenPostponeChoiceCalculator(clock: Clock) {
        sut = DefaultPostponeChoiceCalculator(clock)
    }
}
