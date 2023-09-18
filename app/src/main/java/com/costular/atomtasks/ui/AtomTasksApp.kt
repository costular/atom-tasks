package com.costular.atomtasks.ui

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.costular.atomtasks.Logger
import com.costular.atomtasks.tasks.manager.AutoforwardManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AtomTasksApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var autoforwardManager: AutoforwardManager

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        Logger.invoke(this)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
}
