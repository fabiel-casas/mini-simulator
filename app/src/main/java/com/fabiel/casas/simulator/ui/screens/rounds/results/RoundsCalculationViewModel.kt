package com.fabiel.casas.simulator.ui.screens.rounds.results

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiel.casas.simulator.ui.screens.rounds.RoundItemState
import com.fabiel.casas.simulator.usecase.MatchesUseCase
import com.fabiel.casas.simulator.usecase.SimulatorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
class RoundsCalculationViewModel(
    private val simulatorUseCase: SimulatorUseCase,
    private val matchesUseCase: MatchesUseCase,
) : ViewModel() {

    private val roundState = mutableStateOf<RoundItemState?>(null)
    private val isPlaySimulationEnable = mutableStateOf(true)
    val state = RoundCalculationState(
        roundState = roundState,
        isPlaySimulationEnable = isPlaySimulationEnable,
    )

    fun loadRound(roundId: String?) {
        runCatching {
            roundId?.let { id ->
                matchesUseCase.roundMatches(roundId = id.toInt())
                    .onEach { round ->
                        roundState.value = round
                        isPlaySimulationEnable.value = round.matches.any { it.results == null }
                    }.launchIn(viewModelScope)
            }
        }
    }

    fun startSimulation() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                roundState.value?.let {
                    simulatorUseCase.simulateRound(it)
                } ?: throw Exception("Null round")
            }.onSuccess {
                matchesUseCase.saveMatchSimulation(it)
                isPlaySimulationEnable.value = false
            }
        }
    }
}