package com.costular.atomtasks.ui.base

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.costular.atomtasks.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
abstract class AndroidTest : ComposeProvider {

    @get:Rule
    override val composeTestRule: ComposeTestRule = createAndroidComposeRule<MainActivity>()

}