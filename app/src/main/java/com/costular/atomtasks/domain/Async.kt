package com.costular.atomtasks.domain

sealed class Async<out T> {

    object Uninitialized : Async<Nothing>()

    object Loading : Async<Nothing>()

    data class Success<out T>(val data: T) : Async<T>() {

        operator fun invoke(): T = data

    }

    data class Failure(val throwable: Throwable) : Async<Nothing>()

}