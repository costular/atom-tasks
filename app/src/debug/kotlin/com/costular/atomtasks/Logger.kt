package com.costular.atomtasks

import android.content.Context
import timber.log.Timber

object Logger {
    fun invoke(context: Context) {
        Timber.plant(Timber.DebugTree())
    }
}
