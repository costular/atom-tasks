package com.costular.atomtasks.core.logging

interface AtomLogger {
    fun log(level: LogLevel, tag: String, text: String)
    fun log(level: LogLevel, tag: String, exceptionParams: Map<String, Any?>, throwable: Throwable)

    companion object {

        @PublishedApi
        internal var logger: AtomLogger = NoOpLogger

        fun initialize(logger: AtomLogger) {
            synchronized(this) {
                this.logger = logger
            }
        }
    }
}

inline fun Any.atomLog(
    level: LogLevel? = null,
    tag: String? = null,
    exceptionParams: Map<String, Any?> = emptyMap(),
    subject: () -> Any,
) {
    if (AtomLogger.logger != NoOpLogger) {
        val tagOrCallingClass = tag ?: this::class.java.simpleName

        when (val subjectValue = subject()) {
            is Throwable -> AtomLogger.logger.log(
                level ?: LogLevel.ERROR,
                tagOrCallingClass,
                exceptionParams,
                subjectValue
            )
            else -> AtomLogger.logger.log(
                level ?: LogLevel.DEBUG,
                tagOrCallingClass,
                subjectValue.toString()
            )
        }
    }
}

inline fun atomLog(
    level: LogLevel? = null,
    tag: String? = null,
    exceptionParams: Map<String, Any?> = emptyMap(),
    subject: () -> Any,
) {
    AtomLogger.atomLog(level, tag ?: "", exceptionParams, subject)
}
