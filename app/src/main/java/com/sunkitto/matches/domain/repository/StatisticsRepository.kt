package com.sunkitto.matches.domain.repository

import com.sunkitto.matches.domain.model.Statistics
import kotlinx.coroutines.flow.Flow

interface StatisticsRepository {

    fun getStatistics(): Flow<List<Statistics>>

    fun saveStatistics(statistics: Statistics)
}