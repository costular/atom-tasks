package com.costular.atomtasks.data.manager

import com.costular.atomtasks.domain.manager.ErrorLogger
import com.google.firebase.crashlytics.FirebaseCrashlytics

class ErrorLoggerImpl : ErrorLogger {

    override suspend fun logError(exception: Exception) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }

}
