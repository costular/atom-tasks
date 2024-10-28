package com.costular.atomtasks.data.tutorial

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TutorialModule {
    @Binds
    fun bindsTutorialRepository(
        tutorialRepositoryImpl: TutorialRepositoryImpl,
    ): TutorialRepository
}
