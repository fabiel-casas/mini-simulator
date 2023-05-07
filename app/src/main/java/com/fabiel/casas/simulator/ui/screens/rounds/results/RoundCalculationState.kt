package com.fabiel.casas.simulator.ui.screens.rounds.results

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.fabiel.casas.simulator.ui.screens.rounds.MatchInfo

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
data class RoundCalculationState(
    val roundState: State<RoundSimulationState?> = mutableStateOf(null),
    val isPlaySimulationEnable: State<Boolean> = mutableStateOf(true),
    val isSimulationPlaying: State<Boolean> = mutableStateOf(true),
)


data class RoundSimulationState(
    val roundId: Int,
    val matches: List<MatchInfo>,
)
