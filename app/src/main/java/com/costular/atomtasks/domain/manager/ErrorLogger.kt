package com.costular.atomtasks.domain.manager

import kotlin.Exception

interface ErrorLogger {
    suspend fun logError(exception: Exception)
}
