package com.costular.atomtasks.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.base.AndroidTest
import com.costular.atomtasks.ui.base.getString
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

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
            RemovableChip(
                title = text,
                icon = Icons.Default.Today,
                isSelected = false,
                onClick = { /*TODO*/ },
                onClear = { /*TODO*/ },
            )
        }

        composeTestRule.onNodeWithText(text)
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowClearButton_whenChipIsSelected() {
        composeTestRule.setContent {
            RemovableChip(
                title = "whatever",
                icon = Icons.Default.Today,
                isSelected = true,
                onClick = { /*TODO*/ },
                onClear = { /*TODO*/ },
            )
        }

        clearButton.assertIsDisplayed()
    }

    @Test
    fun shouldCallCallback_whenClickClear_givenChipIsSelected() {
        val callback: () -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            RemovableChip(
                title = "whatever",
                icon = Icons.Default.Today,
                isSelected = true,
                onClick = { /*TODO*/ },
                onClear = callback,
            )
        }

        clearButton.assertIsDisplayed()
            .performClick()

        verify { callback() }
    }

}
