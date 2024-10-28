package com.costular.atomtasks.core.locale

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface LocaleModule {
    @Binds
    fun bindLocaleResolver(impl: LocaleResolverImpl): LocaleResolver
}