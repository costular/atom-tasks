package com.costular.atomtasks.coretesting.ui

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule

val ComposeTestRule.resources
    get() =
        if (this is AndroidComposeTestRule<*, *>) {
            activity.resources
        } else {
            throw IllegalStateException("Resources are only available using AndroidComposeTestRule")
        }

fun ComposeTestRule.getString(@StringRes stringId: Int): String =
    resources.getString(stringId)
