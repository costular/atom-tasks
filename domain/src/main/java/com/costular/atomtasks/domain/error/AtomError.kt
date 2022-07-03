package com.costular.atomtasks.domain.error

sealed class AtomError {

    object NetworkConnection : AtomError()
    object Unknown : AtomError()

    abstract class FeatureFailure : AtomError()
}
