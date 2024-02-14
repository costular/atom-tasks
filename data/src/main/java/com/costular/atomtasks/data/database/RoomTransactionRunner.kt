package com.costular.atomtasks.data.database

import androidx.room.withTransaction
import javax.inject.Inject

class RoomTransactionRunner @Inject constructor(
    private val atomTasksDatabase: AtomTasksDatabase
) : TransactionRunner {
    override suspend fun <T> runAsTransaction(block: suspend () -> T): T =
        atomTasksDatabase.withTransaction(block)
}
