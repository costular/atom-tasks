package com.costular.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.costular.atomtasks.core.testing.interactions.callbackTester
import com.costular.atomtasks.core.testing.interactions.shouldBeCalledOnce
import com.costular.atomtasks.core.testing.ui.AndroidTest
import com.costular.atomtasks.core.testing.ui.getString
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import com.costular.atomtasks.core.ui.R

@HiltAndroidTest
class ClearableChipTest : AndroidTest() {

    private val clearButton by lazy {
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.getString(R.string.content_description_chip_clear),
        )
    }

    @Test
    fun shouldShowText() {
        val text = "whatever just testing"

        composeTestRule.setContent {
            ClearableChip(
                title = text,
                icon = Icons.Default.Today,
                isSelected = false,
                onClick = {},
                onClear = {},
                isError = false,
            )
        }

        composeTestRule.onNodeWithText(text)
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowClearButton_whenChipIsSelected() {
        composeTestRule.setContent {
            ClearableChip(
                title = "whatever",
                icon = Icons.Default.Today,
                isSelected = true,
                onClick = {},
                onClear = {},
                isError = false,
            )
        }

        clearButton.assertIsDisplayed()
    }

    @Test
    fun shouldCallCallback_whenClickClear_givenChipIsSelected() {
        val callback = callbackTester(Unit)

        composeTestRule.setContent {
            ClearableChip(
                title = "whatever",
                icon = Icons.Default.Today,
                isSelected = true,
                onClick = {},
                onClear = callback,
                isError = false,
            )
        }

        clearButton.assertIsDisplayed()
            .performClick()

        callback.shouldBeCalledOnce()
    }
}
