package com.fabiel.casas.simulator.ui.screens.standings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiel.casas.simulator.usecase.MatchesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Mingle Sport Tech
 * Created on 07/05/2023.
 */
class StandingsViewModels(
    private val matchesUseCase: MatchesUseCase,
) : ViewModel() {

    private val standings = mutableStateOf<List<StandingsItemState>>(emptyList())
    val state: StandingsState = StandingsState(
        standings = standings
    )

    init {
        observeMatchChanges()
    }

    private fun observeMatchChanges() {
        matchesUseCase.getStandings()
            .onEach {
                standings.value = it
            }.launchIn(viewModelScope)
    }
}