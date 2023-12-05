package com.costular.atomtasks.review.usecase

import com.costular.core.Either
import com.google.common.truth.Truth.assertThat
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ShouldAskReviewUseCaseTest {

    lateinit var sut: ShouldAskReviewUseCase

    @Test
    fun `Should return same value as review strategy`(@TestParameter shouldAskReview: Boolean) =
        runTest {
            initialize(shouldAskReview)

            val result = sut.invoke(Unit)

            assertThat(result).isEqualTo(Either.Result(shouldAskReview))
        }

    private fun initialize(shouldAskReview: Boolean) {
        sut = ShouldAskReviewUseCase(FakeReviewStrategy(shouldAskReview))
    }
}
