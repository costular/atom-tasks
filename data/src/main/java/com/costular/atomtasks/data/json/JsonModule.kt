package com.costular.atomtasks.data.json

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonModule {
    @Provides
    @Singleton
    fun provideKotlinSerializer(): Json =
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            prettyPrint = true
            coerceInputValues = true
            encodeDefaults = true
            allowSpecialFloatingPointValues = true
        }
}