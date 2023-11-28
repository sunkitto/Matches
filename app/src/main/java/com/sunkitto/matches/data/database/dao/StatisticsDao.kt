package com.sunkitto.matches.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sunkitto.matches.data.database.model.StatisticsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticsDao {

    @Query("SELECT * FROM statistics ORDER BY date DESC")
    fun getStatistics(): Flow<List<StatisticsEntity>>

    @Insert
    fun saveStatistics(statistics: StatisticsEntity)
}