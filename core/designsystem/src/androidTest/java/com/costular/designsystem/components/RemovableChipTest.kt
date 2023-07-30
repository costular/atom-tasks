package com.costular.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.costular.atomtasks.core.testing.ui.AndroidTest
import com.costular.atomtasks.core.testing.ui.getString
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import com.costular.atomtasks.core.ui.R

@HiltAndroidTest
class RemovableChipTest : AndroidTest() {

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
            )
        }

        clearButton.assertIsDisplayed()
    }

    @Test
    fun shouldCallCallback_whenClickClear_givenChipIsSelected() {
        val callback: () -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            ClearableChip(
                title = "whatever",
                icon = Icons.Default.Today,
                isSelected = true,
                onClick = {},
                onClear = callback,
            )
        }

        clearButton.assertIsDisplayed()
            .performClick()

        verify { callback() }
    }
}
