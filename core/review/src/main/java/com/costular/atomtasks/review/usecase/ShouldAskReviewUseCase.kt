package com.costular.atomtasks.review.usecase

import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.review.model.ShouldAskReviewError
import com.costular.atomtasks.review.strategy.ReviewStrategy
import com.costular.core.Either
import com.costular.core.usecase.UseCase
import java.lang.Exception
import javax.inject.Inject

class ShouldAskReviewUseCase @Inject constructor(
    private val reviewStrategy: ReviewStrategy,
) : UseCase<Unit, Either<ShouldAskReviewError, Boolean>> {
    override suspend fun invoke(params: Unit): Either<ShouldAskReviewError, Boolean> =
        try {
            val result = reviewStrategy.shouldShowReview()
            Either.Result(result)
        } catch (e: Exception) {
            atomLog { e }
            Either.Error(ShouldAskReviewError.UnknownError)
        }

}
