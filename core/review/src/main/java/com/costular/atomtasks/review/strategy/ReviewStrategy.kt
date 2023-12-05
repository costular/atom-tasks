package com.costular.atomtasks.review.strategy

interface ReviewStrategy {
    suspend fun shouldShowReview(): Boolean
}
