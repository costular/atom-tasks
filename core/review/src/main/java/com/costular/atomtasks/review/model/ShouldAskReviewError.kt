package com.costular.atomtasks.review.model

sealed interface ShouldAskReviewError {
    data object UnknownError : ShouldAskReviewError
}
