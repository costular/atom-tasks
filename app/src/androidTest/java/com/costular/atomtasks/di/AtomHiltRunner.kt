package com.costular.atomtasks.di

import android.app.Application
import android.content.Context
import com.karumi.shot.ShotTestRunner
import dagger.hilt.android.testing.HiltTestApplication

class AtomHiltRunner : ShotTestRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
