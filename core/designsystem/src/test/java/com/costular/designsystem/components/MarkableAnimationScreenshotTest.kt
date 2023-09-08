package com.costular.designsystem.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboGif
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class MarkableAnimationScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun captureMarkableAnimation() {
        composeTestRule.setContent {
            var isMarked by remember { mutableStateOf(false) }

            Markable(
                isMarked = isMarked,
                onClick = {},
                contentColor = MaterialTheme.colorScheme.primaryContainer,
                onContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                borderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )

            composeTestRule.onRoot()
                .captureRoboGif(composeTestRule, filePath = "build/markable-animation.gif") {
                    isMarked = true
                }
        }
    }

}
