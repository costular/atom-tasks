package com.costular.atomtasks.core.testing.interactions

import com.google.common.truth.Truth.assertThat

class CallbackTester<Value>(private val block: () -> Value) : () -> Value {
    var invokeCount = 0
    override operator fun invoke(): Value {
        invokeCount++
        return block()
    }
}

fun <Value> callbackTester(computedValue: Value) =
    CallbackTester { computedValue }

fun <T> CallbackTester<T>.shouldBeCalledOnce() {
    assertThat(invokeCount).isEqualTo(1)
}
