package com.costular.atomtasks.screenshots.components

import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.ScreenHeader
import org.junit.Test

@ScreenshotTest
class ScreenHeaderSnapshotTest : SnapshotTest() {

    @Test
    fun header() {
        runScreenshotTest {
            ScreenHeader(text = "Screen header")
        }
    }
}
