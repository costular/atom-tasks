package com.costular.atomtasks.data.tutorial

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TutorialModule {
    @Binds
    abstract fun bindsTutorialRepository(
        tutorialRepositoryImpl: TutorialRepositoryImpl,
    ): TutorialRepository
}
