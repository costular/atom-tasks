package com.costular.atomhabits.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun <T : Comparable<T>> SlideInOutLayout(
    targetState: T,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    content: @Composable (T) -> Unit
) {
    val items = remember { mutableStateListOf<CrossSlideAnimationItem<T>>() }
    val transitionState = remember { MutableTransitionState(targetState) }
    val targetChanged = (targetState != transitionState.targetState)
    transitionState.targetState = targetState
    val transition = updateTransition(transitionState)
    if (targetChanged || items.isEmpty()) {
        // Only manipulate the list when the state is changed, or in the first run.
        val keys = items.map { it.key }.run {
            if (!contains(targetState)) {
                toMutableList().also { it.add(targetState) }
            } else {
                this
            }
        }
        items.clear()
        keys.mapTo(items) { key ->
            CrossSlideAnimationItem(key) {
                val visibility = transition.animateFloat(
                    transitionSpec = { animationSpec }, label = ""
                ) {
                    when {
                        it == key -> 0f
                        it > key -> -1f
                        else -> 1f
                    }
                }
                Box(Modifier.then(PercentageLayoutOffset(rawOffset = visibility))) {
                    content(key)
                }
            }
        }
    } else if (transitionState.currentState == transitionState.targetState) {
        // Remove all the intermediate items from the list once the animation is finished.
        items.removeAll { it.key != transitionState.targetState }
    }

    Box(modifier) {
        items.forEach {
            key(it.key) {
                it.content()
            }
        }
    }
}

data class CrossSlideAnimationItem<T>(
    val key: T,
    val content: @Composable () -> Unit
)

// Taken from https://github.com/zach-klippenstein/compose-backstack
class PercentageLayoutOffset(private val rawOffset: State<Float>) : LayoutModifier {
    private val offset = { rawOffset.value.coerceIn(-1f..1f) }

    private fun offsetPosition(containerSize: IntSize) = IntOffset(
        // RTL is handled automatically by place.
        x = (containerSize.width * offset()).toInt(),
        y = 0
    )

    override fun toString(): String = "${this::class.java.simpleName}(offset=$offset)"
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: androidx.compose.ui.unit.Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.place(offsetPosition(IntSize(placeable.width, placeable.height)))
        }
    }
}