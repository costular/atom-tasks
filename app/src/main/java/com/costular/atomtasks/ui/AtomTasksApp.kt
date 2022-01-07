package com.costular.atomtasks.ui

import android.app.Application
import com.costular.atomtasks.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AtomTasksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}