package com.sunkitto.matches.features.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunkitto.matches.R
import com.sunkitto.matches.databinding.ItemStatisticsBinding
import com.sunkitto.matches.ui.model.StatisticsUi

class StatisticsAdapter : ListAdapter<StatisticsUi, RecyclerView.ViewHolder>(
    StatisticsDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        StatisticsViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val statisticsUi = getItem(position)
        statisticsUi.let {
            if(holder is StatisticsViewHolder) {
                holder.bind(
                    statisticsUi = statisticsUi
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        R.layout.item_statistics

    class StatisticsViewHolder(
        private val binding: ItemStatisticsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(statisticsUi: StatisticsUi) {

            with(binding) {
                mistakesTextView.text = statisticsUi.mistakes
                dateTextView.text = statisticsUi.date
            }
        }

        companion object {

            @JvmStatic
            fun create(parent: ViewGroup): StatisticsViewHolder =
                StatisticsViewHolder(
                    ItemStatisticsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                )
        }
    }
}