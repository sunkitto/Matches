package com.sunkitto.matches.features.matches

import androidx.lifecycle.ViewModel
import com.sunkitto.matches.data.repository.StatisticsRepositoryImpl
import com.sunkitto.matches.domain.model.MemoryCard
import com.sunkitto.matches.domain.model.Statistics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepositoryImpl,
) : ViewModel() {

    private val _mistakesState: MutableStateFlow<Int> = MutableStateFlow(0)
    val mistakesState: StateFlow<Int> = _mistakesState.asStateFlow()

    private var selectedCardIndex: Int? = null
    private var passedCardsNumber: Int = 0
    var memoryCards: List<MemoryCard> = listOf()

    fun flipCard(index: Int, onNavigateToResults: () -> Unit) {

        if(memoryCards[index].isFlipped) return

        if(selectedCardIndex != null) {
            if(memoryCards[selectedCardIndex!!].id == memoryCards[index].id) {
                memoryCards[selectedCardIndex!!].isMatched = true
                memoryCards[index].isMatched = true
                passedCardsNumber++
                if(passedCardsNumber == memoryCards.size / 2) {
                    finishGame(onNavigateToResults)
                }
            } else {
                _mistakesState.value++
            }
            selectedCardIndex = null
        } else {
            for(memoryCard in memoryCards) {
                if(!memoryCard.isMatched) {
                    memoryCard.isFlipped = false
                }
            }
            selectedCardIndex = index
        }

        memoryCards[index].isFlipped = !memoryCards[index].isFlipped
    }

    private fun finishGame(onNavigateToResults: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            statisticsRepository.saveStatistics(
                Statistics(
                    date = Clock.System.now(),
                    mistakes = _mistakesState.value,
                )
            )
        }
        onNavigateToResults()
    }
}