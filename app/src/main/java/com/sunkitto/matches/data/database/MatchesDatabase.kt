package com.sunkitto.matches.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sunkitto.matches.data.database.converters.InstantConverter
import com.sunkitto.matches.data.database.dao.StatisticsDao
import com.sunkitto.matches.data.database.model.StatisticsEntity

@Database(
    entities = [StatisticsEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    InstantConverter::class
)
abstract class MatchesDatabase : RoomDatabase() {

    abstract fun statisticsDao(): StatisticsDao
}