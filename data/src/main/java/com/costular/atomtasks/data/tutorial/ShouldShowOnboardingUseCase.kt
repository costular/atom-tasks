package com.costular.atomtasks.data.tutorial

import com.costular.atomtasks.core.AtomError
import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShouldShowOnboardingUseCase @Inject constructor(
    private val tutorialRepository: TutorialRepository,
) : UseCase<Unit, Either<AtomError, Flow<Boolean>>> {
    override suspend fun invoke(params: Unit): Either<AtomError, Flow<Boolean>> {
        return tutorialRepository.shouldShowOnboarding()
    }
}