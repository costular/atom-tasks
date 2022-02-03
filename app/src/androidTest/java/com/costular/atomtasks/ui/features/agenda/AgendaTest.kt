package com.costular.atomtasks.ui.features.agenda

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.base.AndroidTest
import com.costular.atomtasks.ui.base.getString
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposeUiApi
@HiltAndroidTest
class AgendaTest : AndroidTest() {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun shouldChangeSelectedDay_whenClickPrevDay() {
        agenda {
            goPrevDay()
            assertDayText(composeTestRule.getString(R.string.day_yesterday))
        }
    }

    @Test
    fun shouldChangeSelectedDay_whenClickNextDay() {
        agenda {
            goNextDay()
            assertDayText(composeTestRule.getString(R.string.day_tomorrow))
        }
    }

    @Test
    fun shouldChangeSelectedDayToToday_whenClickToday() {
        agenda {
            goNextDay()
            goToday()
            assertDayText(composeTestRule.getString(R.string.today))
        }
    }

    @Test
    fun shouldCreateATask_whenClickOnCreateNewTaskTypeAndThenSave() {
        val taskName = "this is a test :D"

        agenda {
            openCreateTask {
                type(taskName)
                assertSaveIsDisplayed()
                save()
            }
        }

        agenda {
            firstTaskHasText(taskName)
        }
    }

}