package com.costular.atomhabits.data.db

import androidx.room.TypeConverter
import java.time.LocalDate

object DbTypeConverters {

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate): String = localDate.toString()

    @TypeConverter
    fun toLocalDate(input: String): LocalDate = LocalDate.parse(input)

}