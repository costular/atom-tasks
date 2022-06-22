package com.costular.atomtasks.screenshots.components

import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.ScreenSubheader
import org.junit.Test

@ScreenshotTest
class ScreenSubheaderSnapshotTest : SnapshotTest() {

    @Test
    fun subheader() {
        runScreenshotTest {
            ScreenSubheader(text = "Screen subheader")
        }
    }
}
