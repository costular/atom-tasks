package com.costular.atomtasks.coretesting.ui

import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
abstract class AndroidTest : ComposeProvider {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    override val composeTestRule = createComposeRule()

    @Before
    open fun setUp() {
        hiltRule.inject()
    }
}
