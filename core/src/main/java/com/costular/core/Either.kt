package com.costular.core

import com.costular.core.Either.Error
import com.costular.core.Either.Result

sealed class Either<out Error, out Result> {
    data class Error<out Error>(val error: Error) : Either<Error, Nothing>()
    data class Result<out Result>(val result: Result) : Either<Nothing, Result>()

    inline fun <T> fold(
        ifError: (Error) -> T,
        ifResult: (Result) -> T,
    ): T =
        when (this) {
            is Either.Error -> ifError(error)
            is Either.Result -> ifResult(result)
        }

    inline fun <R> map(
        fn: (Result) -> R,
    ): Either<Error, R> =
        fold({ Error(it) }, { Result(fn(it)) })

    inline fun <E> mapError(
        fn: (Error) -> E,
    ): Either<E, Result> =
        fold({ Error(fn(it)) }, { Result(it) })

    inline fun tap(
        fn: (Result) -> Unit,
    ): Either<Error, Result> =
        when (this) {
            is Either.Error -> this
            is Either.Result -> {
                fn(this.result)
                this
            }
        }

    inline fun tapError(
        fn: (Error) -> Unit,
    ): Either<Error, Result> =
        when (this) {
            is Either.Result -> this
            is Either.Error -> {
                fn(this.error)
                this
            }
        }
}

inline fun <E, R, R2> Either<E, R>.flatMap(
    fn: (R) -> Either<E, R2>,
): Either<E, R2> =
    when (this) {
        is Result -> fn(result)
        is Error -> this
    }

/**
 * Returns the value from this [Either.Result] or the given argument if this is a [Either.Error].
 *
 * Example:
 * ```
 * Either.Result(12).getOrElse(17) // Result: 12
 * Either.Error(12).getOrElse(17)  // Result: 17
 * ```
 */
inline fun <Result> Either<*, Result>.getOrElse(default: () -> Result): Result =
    fold(
        ifError = { default() },
        ifResult = { it },
    )

/**
 * Returns the value from this [Either.Result] or null if this is a [Either.Error].
 *
 * Example:
 * ```
 * Either.Result(12).orNull() // Result: 12
 * Either.Error(12).orNull()  // Result: null
 * ```
 */
fun <Result> Either<*, Result>.orNull(): Result? = getOrElse { null }

fun <T> T.toError(): Error<T> = Error(this)

fun <T> T.toResult(): Result<T> = Result(this)
