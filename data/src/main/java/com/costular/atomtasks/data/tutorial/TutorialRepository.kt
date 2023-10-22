package com.costular.atomtasks.data.tutorial

import kotlinx.coroutines.flow.Flow

interface TutorialRepository {
    fun shouldShowReorderTaskTutorial(): Flow<Boolean>
    suspend fun reorderTaskShown()
}
