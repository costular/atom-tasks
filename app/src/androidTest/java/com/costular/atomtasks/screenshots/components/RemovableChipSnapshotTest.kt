package com.costular.atomtasks.screenshots.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.RemovableChip
import org.junit.Test

@ScreenshotTest
class RemovableChipSnapshotTest : SnapshotTest() {

    @Test
    fun removableChipUnselected() {
        runScreenshotTest {
            RemovableChip(
                title = "Reminder",
                icon = Icons.Default.CalendarToday,
                isSelected = false,
                onClick = {},
                onClear = {},
            )
        }
    }

    @Test
    fun removableChipSelected() {
        runScreenshotTest {
            RemovableChip(
                title = "Reminder",
                icon = Icons.Default.CalendarToday,
                isSelected = true,
                onClick = {},
                onClear = {},
            )
        }
    }
}
