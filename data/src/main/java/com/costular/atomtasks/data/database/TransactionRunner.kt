package com.costular.atomtasks.data.database

interface TransactionRunner {
    suspend fun <T> runAsTransaction(block: suspend () -> T): T
}
