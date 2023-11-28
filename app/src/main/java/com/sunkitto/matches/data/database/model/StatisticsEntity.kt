package com.sunkitto.matches.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "statistics")
data class StatisticsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Instant,
    val mistakes: Int,
)
