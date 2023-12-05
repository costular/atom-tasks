package com.costular.atomtasks.review.di

import com.costular.atomtasks.review.strategy.ReviewStrategy
import com.costular.atomtasks.review.strategy.ReviewStrategyImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface ReviewStrategyModule {
    @Binds
    fun bindsReviewStrategy(impl: ReviewStrategyImpl): ReviewStrategy
}
