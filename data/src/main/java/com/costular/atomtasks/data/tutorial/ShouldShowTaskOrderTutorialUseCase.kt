package com.costular.atomtasks.data.tutorial

import com.costular.atomtasks.core.usecase.UseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ShouldShowTaskOrderTutorialUseCase @Inject constructor(
    private val tutorialRepository: TutorialRepository,
) : UseCase<Unit, Flow<Boolean>> {
    override suspend fun invoke(params: Unit): Flow<Boolean> =
        tutorialRepository.shouldShowReorderTaskTutorial()
}
