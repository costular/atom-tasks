package com.costular.atomtasks.ui.dialogs.timepicker

import app.cash.turbine.test
import com.costular.atomtasks.MviViewModelTest
import com.google.common.truth.Truth
import java.time.LocalTime
import kotlin.time.ExperimentalTime
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class TimePickerDialogViewModelTest : MviViewModelTest() {

    private lateinit var sut: TimePickerDialogViewModel

    @Before
    fun setUp() {
        sut = TimePickerDialogViewModel()
    }

    @Test
    fun `should update state accordingly when set state`() = testBlocking {
        val time = LocalTime.of(9, 0)

        sut.setTime(time)

        sut.state.test {
            Truth.assertThat(expectMostRecentItem().time).isEqualTo(time)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should send cancel event when cancel`() = testBlocking {
        sut.cancel()

        sut.uiEvents.test {
            Truth.assertThat(expectMostRecentItem())
                .isInstanceOf(TimePickerUiEvents.Cancel::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should send save event when save`() = testBlocking {
        sut.save()

        sut.uiEvents.test {
            Truth.assertThat(expectMostRecentItem())
                .isInstanceOf(TimePickerUiEvents.Save::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

}
