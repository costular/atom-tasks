package com.costular.designsystem.util

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.supportWideScreen(max: Dp = 840.dp) = this
    .fillMaxWidth()
    .wrapContentWidth(align = Alignment.CenterHorizontally)
    .widthIn(max = max)

fun Modifier.readOnlyClickable(onClick: () -> Unit) = this.pointerInput(Unit) {
    awaitEachGesture {
        awaitFirstDown(pass = PointerEventPass.Initial)
        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
        if (upEvent != null) {
            onClick()
        }
    }
}
