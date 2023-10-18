package com.costular.atomtasks.postponetask.di

import com.costular.atomtasks.postponetask.domain.DefaultPostponeChoiceCalculator
import com.costular.atomtasks.postponetask.domain.PostponeChoiceCalculator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Clock

@InstallIn(SingletonComponent::class)
@Module
class PostponeChoiceCalculatorModule {
    @Provides
    fun providesPostponeChoiceCalculator(): PostponeChoiceCalculator {
        return DefaultPostponeChoiceCalculator(Clock.systemDefaultZone())
    }
}
