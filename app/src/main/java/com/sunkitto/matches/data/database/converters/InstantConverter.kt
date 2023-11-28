package com.sunkitto.matches.data.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class InstantConverter {

    @TypeConverter
    fun longToInstant(value: Long): Instant =
        Instant.fromEpochMilliseconds(value)

    @TypeConverter
    fun instantToLong(value: Instant): Long =
        value.toEpochMilliseconds()
}