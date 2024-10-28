package com.costular.atomtasks.core

sealed interface AtomError {
    data object ConnectivityError : AtomError
    data object UnknownError : AtomError
}