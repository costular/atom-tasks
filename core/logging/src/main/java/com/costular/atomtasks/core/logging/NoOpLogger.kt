package com.costular.atomtasks.core.logging

object NoOpLogger : AtomLogger {
    override fun log(level: LogLevel, tag: String, text: String) {
    }

    override fun log(
        level: LogLevel,
        tag: String,
        exceptionParams: Map<String, Any?>,
        throwable: Throwable
    ) {
    }
}
