package com.sunkitto.matches.data.repository

import com.sunkitto.matches.data.database.MatchesDatabase
import com.sunkitto.matches.domain.model.Statistics
import com.sunkitto.matches.domain.model.asStatistic
import com.sunkitto.matches.domain.model.asStatisticEntity
import com.sunkitto.matches.domain.repository.StatisticsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val matchesDatabase: MatchesDatabase,
) : StatisticsRepository {

    override fun getStatistics(): Flow<List<Statistics>> =
        matchesDatabase.statisticsDao().getStatistics().map { statisticsEntities ->
            statisticsEntities.map { statisticsEntity ->
                statisticsEntity.asStatistic()
            }
        }

    override fun saveStatistics(statistics: Statistics) =
        matchesDatabase.statisticsDao().saveStatistics(
            statistics.asStatisticEntity()
        )
}