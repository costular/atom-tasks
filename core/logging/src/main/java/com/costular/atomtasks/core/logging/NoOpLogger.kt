package com.costular.atomtasks.core.logging

object NoOpLogger : AtomLogger {
    @Suppress("EmptyFunctionBlock")
    override fun log(level: LogLevel, tag: String, text: String) {
    }

    @Suppress("EmptyFunctionBlock")
    override fun log(
        level: LogLevel,
        tag: String,
        exceptionParams: Map<String, Any?>,
        throwable: Throwable
    ) {
    }
}
