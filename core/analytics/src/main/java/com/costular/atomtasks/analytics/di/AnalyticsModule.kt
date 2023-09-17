package com.costular.atomtasks.analytics.di

import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.analytics.providers.FirebaseAnalyticsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AnalyticsModule {

    @Binds
    internal abstract fun bindAtomAnalytics(firebase: FirebaseAnalyticsProvider): AtomAnalytics

}
