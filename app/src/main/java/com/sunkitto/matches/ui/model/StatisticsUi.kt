package com.sunkitto.matches.ui.model

import com.sunkitto.matches.domain.model.Statistics
import com.sunkitto.matches.util.formatDate

data class StatisticsUi(
    val date: String,
    var mistakes: String,
)

fun Statistics.asStatisticsUi() =
    StatisticsUi(
        date = date.formatDate(),
        mistakes = mistakes.toString(),
    )