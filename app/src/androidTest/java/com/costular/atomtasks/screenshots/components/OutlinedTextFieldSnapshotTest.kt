package com.costular.atomtasks.screenshots.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.AtomOutlinedTextField
import org.junit.Test

@ScreenshotTest
class OutlinedTextFieldSnapshotTest : SnapshotTest() {

    @Test
    fun outlinedTextField() {
        runScreenshotTest {
            AtomOutlinedTextField(
                value = "Input text...",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    @Test
    fun emptyOutlinedTextField() {
        runScreenshotTest {
            AtomOutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
