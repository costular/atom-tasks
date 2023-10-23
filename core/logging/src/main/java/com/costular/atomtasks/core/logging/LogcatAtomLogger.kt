package com.costular.atomtasks.core.logging

import android.util.Log

class LogcatAtomLogger : AtomLogger {
    override fun log(level: LogLevel, tag: String, text: String) {
        Log.println(level.asLogcatLevel(), tag, text)
    }

    override fun log(
        level: LogLevel,
        tag: String,
        exceptionParams: Map<String, Any?>,
        throwable: Throwable
    ) {
        val androidLogLevel = level.asLogcatLevel()

        Log.println(androidLogLevel, tag, throwable.stackTraceToString())

        exceptionParams.forEach {
            Log.println(androidLogLevel, tag, "${it.key} ==> ${it.value}")
        }
    }

    private fun LogLevel.asLogcatLevel(): Int =
        when (this) {
            LogLevel.VERBOSE -> Log.VERBOSE
            LogLevel.DEBUG -> Log.DEBUG
            LogLevel.INFO -> Log.INFO
            LogLevel.WARN -> Log.WARN
            LogLevel.ERROR -> Log.ERROR
        }
}
