package com.costular.atomtasks.screenshots.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.ui.Modifier
import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.AtomTopBar
import org.junit.Test

@ScreenshotTest
class AtomTopBarSnapshotTest : SnapshotTest() {

    @Test
    fun atomTopBarWithBackIconAndText() {
        runScreenshotTest {
            AtomTopBar(
                title = {
                    Text("Atom")
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    }

    @Test
    fun atomTopBarWithBackIconTitleAndActions() {
        runScreenshotTest {
            AtomTopBar(
                title = {
                    Text("Atom")
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Favorite, contentDescription = null)
                    }
                },
            )
        }
    }
}
