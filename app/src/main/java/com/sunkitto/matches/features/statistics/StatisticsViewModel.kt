package com.sunkitto.matches.features.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.matches.data.repository.StatisticsRepositoryImpl
import com.sunkitto.matches.ui.model.StatisticsUi
import com.sunkitto.matches.ui.model.asStatisticsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepositoryImpl,
) : ViewModel() {

    private val _state = MutableStateFlow<List<StatisticsUi>>(listOf())
    val state: StateFlow<List<StatisticsUi>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            statisticsRepository.getStatistics().collectLatest { statistics ->
                _state.value = statistics.map { statistic ->
                    statistic.asStatisticsUi()
                }
            }
        }
    }
}