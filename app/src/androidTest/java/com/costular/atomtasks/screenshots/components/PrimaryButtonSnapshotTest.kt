package com.costular.atomtasks.screenshots.components

import androidx.compose.material.Text
import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.PrimaryButton
import org.junit.Test

@ScreenshotTest
class PrimaryButtonSnapshotTest : SnapshotTest() {

    @Test
    fun primaryButton() {
        runScreenshotTest {
            PrimaryButton(onClick = {}) {
                Text("Test me!")
            }
        }
    }
}
