package com.fabiel.casas.simulator.ui.screens.rounds

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiel.casas.simulator.usecase.MatchesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
class RoundListViewModel(
    private val matchesUseCase: MatchesUseCase
) : ViewModel() {

    private val roundList = mutableStateOf<List<RoundItemState>>(emptyList())
    private val isCreateMatchesVisible = mutableStateOf(false)
    val state = RoundsState(
        roundList = roundList,
        isCreateMatchesVisible = isCreateMatchesVisible
    )

    init {
        matchesUseCase.matchesFlow()
            .onEach { rounds ->
                roundList.value = rounds
                isCreateMatchesVisible.value = isCreateMatchesVisible(rounds)
            }.launchIn(viewModelScope)
    }

    private fun isCreateMatchesVisible(rounds: List<RoundItemState>) =
        rounds.isEmpty() || rounds.all { item ->
            item.matches.all { it.results != null }
        }

    fun createMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            matchesUseCase.createRoundsMatches()
        }
    }
}