package com.costular.atomtasks.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.costular.commonui.theme.AtomRemindersTheme
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.rules.TestName
import org.junit.runner.RunWith

@RunWith(value = TestParameterInjector::class)
abstract class SnapshotTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val testNameRule = TestName()

    @TestParameter
    private lateinit var themeMode: ThemeMode

    fun runScreenshotTest(
        content: @Composable () -> Unit,
    ) {
        composeTestRule.setContent {
            AtomRemindersTheme(
                darkTheme = themeMode == ThemeMode.DARK,
                content = content,
            )
        }

        val name = "${testNameRule.methodName}_${themeMode.name.lowercase()}"

        compareScreenshot(
            rule = composeTestRule,
            name = name,
        )
    }

    private enum class ThemeMode { LIGHT, DARK }
}
