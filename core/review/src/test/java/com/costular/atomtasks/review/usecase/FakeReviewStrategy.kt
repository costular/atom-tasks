package com.costular.atomtasks.review.usecase

import com.costular.atomtasks.review.strategy.ReviewStrategy

class FakeReviewStrategy(val shouldReview: Boolean) : ReviewStrategy {
    override suspend fun shouldShowReview(): Boolean = shouldReview
}
