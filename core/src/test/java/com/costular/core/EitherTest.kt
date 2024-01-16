package com.costular.core

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.getOrElse
import com.costular.atomtasks.core.orNull
import com.google.common.truth.Truth
import org.junit.Test

class EitherTest {

    @Test
    fun `Should return Result value when calling getOrElse() with Either Result`() {
        Truth.assertThat(Either.Result(1).getOrElse { 2 }).isEqualTo(1)
    }

    @Test
    fun `Should return default value when calling getOrElse() with Either Error`() {
        val result = Either.Error(1).getOrElse { 2 }
        Truth.assertThat(result).isEqualTo(2)
    }

    @Test
    fun `Should return Result when calling orNull() with Either Result`() {
        Truth.assertThat(Either.Result(100).orNull()).isEqualTo(100)
    }

    @Test
    fun `Should return null when calling orNull() with Either Error`() {
        Truth.assertThat(Either.Error(1).orNull<Any>()).isNull()
    }
}
