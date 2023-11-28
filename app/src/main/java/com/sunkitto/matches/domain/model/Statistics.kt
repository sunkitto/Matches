package com.sunkitto.matches.domain.model

import com.sunkitto.matches.data.database.model.StatisticsEntity
import kotlinx.datetime.Instant

data class Statistics(
    val date: Instant,
    var mistakes: Int,
)

fun StatisticsEntity.asStatistic() =
    Statistics(
        date = date,
        mistakes = mistakes,
    )

fun Statistics.asStatisticEntity() =
    StatisticsEntity(
        date = date,
        mistakes = mistakes,
    )