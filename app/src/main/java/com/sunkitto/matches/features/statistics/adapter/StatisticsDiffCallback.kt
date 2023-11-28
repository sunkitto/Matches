package com.sunkitto.matches.features.statistics.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sunkitto.matches.ui.model.StatisticsUi

class StatisticsDiffCallback : DiffUtil.ItemCallback<StatisticsUi>() {

    override fun areItemsTheSame(
        oldItem: StatisticsUi,
        newItem: StatisticsUi,
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: StatisticsUi,
        newItem: StatisticsUi,
    ): Boolean =
        oldItem == newItem
}