package com.fabiel.casas.simulator.ui.screens.rounds.results

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.fabiel.casas.simulator.ui.screens.rounds.RoundItemState

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
data class RoundCalculationState(
    val roundState: State<RoundItemState?> = mutableStateOf(null),
    val isPlaySimulationEnable: State<Boolean> = mutableStateOf(true),
)
