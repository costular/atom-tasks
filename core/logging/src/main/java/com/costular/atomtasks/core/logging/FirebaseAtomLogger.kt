package com.costular.atomtasks.core.logging

import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirebaseAtomLogger(
    private val crashlytics: FirebaseCrashlytics
) : AtomLogger {
    override fun log(level: LogLevel, tag: String, text: String) {
        crashlytics.log("${System.currentTimeMillis()} | [${level.name}] | $tag: $text")
    }

    override fun log(
        level: LogLevel,
        tag: String,
        exceptionParams: Map<String, Any?>,
        throwable: Throwable
    ) {
        crashlytics.apply {
            val paramsFormed = exceptionParams.entries.joinToString("") {
                "${it.key}=${it.value};"
            }
            setCustomKey("atom-logger", paramsFormed)
            recordException(throwable)
        }
    }
}
