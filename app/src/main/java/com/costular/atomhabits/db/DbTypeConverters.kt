package com.costular.atomhabits.db

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

object DbTypeConverters {

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate): String = localDate.toString()

    @TypeConverter
    fun toLocalDate(input: String): LocalDate = LocalDate.parse(input)

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime): String = localTime.toString()

    @TypeConverter
    fun toLocalTime(input: String): LocalTime = LocalTime.parse(input)

    @TypeConverter
    fun fromInstant(instant: Instant): String = instant.toString()

    @TypeConverter
    fun toInstant(input: String): Instant = Instant.parse(input)

}