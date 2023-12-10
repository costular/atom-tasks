package com.costular.atomtasks.core.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<in Params, out ReturnType> {
    suspend operator fun invoke(params: Params): ReturnType
}

interface ObservableUseCase<in Params, out ReturnType> {
    operator fun invoke(params: Params): Flow<ReturnType>
}

typealias EmptyParams = Unit
suspend operator fun <ReturnType> UseCase<EmptyParams, ReturnType>.invoke() = invoke(EmptyParams)

operator fun <ReturnType> ObservableUseCase<EmptyParams, ReturnType>.invoke() = invoke(EmptyParams)
