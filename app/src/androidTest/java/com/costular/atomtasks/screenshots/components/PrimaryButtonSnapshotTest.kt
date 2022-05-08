package com.costular.atomtasks.screenshots.components

import androidx.compose.material.Text
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.PrimaryButton
import org.junit.Test

class PrimaryButtonSnapshotTest : SnapshotTest() {

    @Test
    fun primaryButton() {
        runScreenshotTest {
            PrimaryButton(onClick = { /*TODO*/ }) {
                Text("Test me!")
            }
        }
    }
}
