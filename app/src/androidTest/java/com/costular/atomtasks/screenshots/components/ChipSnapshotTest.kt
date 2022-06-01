package com.costular.atomtasks.screenshots.components

import androidx.compose.material.Text
import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.Chip
import org.junit.Test

@ScreenshotTest
class ChipSnapshotTest : SnapshotTest() {

    @Test
    fun chipText() {
        runScreenshotTest {
            Chip(onClick = {}) {
                Text("Chip test")
            }
        }
    }
}
