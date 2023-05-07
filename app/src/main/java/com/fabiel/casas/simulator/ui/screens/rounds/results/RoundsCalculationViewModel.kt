package com.fabiel.casas.simulator.ui.screens.rounds.results

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiel.casas.simulator.ui.screens.rounds.MatchInfo
import com.fabiel.casas.simulator.usecase.MatchesUseCase
import com.fabiel.casas.simulator.usecase.Possession
import com.fabiel.casas.simulator.usecase.SimulatorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
class RoundsCalculationViewModel(
    private val simulatorUseCase: SimulatorUseCase,
    private val matchesUseCase: MatchesUseCase,
) : ViewModel() {

    private val roundState = mutableStateOf<RoundSimulationState?>(null)
    private val isPlaySimulationEnable = mutableStateOf(true)
    private val isSimulationPlaying = mutableStateOf(true)
    val state = RoundCalculationState(
        roundState = roundState,
        isPlaySimulationEnable = isPlaySimulationEnable,
        isSimulationPlaying = isSimulationPlaying,
    )

    fun loadRound(roundId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                roundId?.let { id ->
                    val round = matchesUseCase.roundMatches(roundId = id.toInt())
                    roundState.value = round
                    isPlaySimulationEnable.value = round.matches.any { it.results == null }
                }
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
                isSimulationPlaying.value = true
                startSimulationAnimation(it)
            }
        }
    }

    private fun startSimulationAnimation(roundSimulationState: RoundSimulationState) {
        roundSimulationState.matches.forEach { matchInfo ->
            var homeScore = 0
            var awayScore = 0
            viewModelScope.launch(Dispatchers.IO) {
                matchInfo.matchTimeLine.forEach { action ->
                    when {
                        action.isGoal && action.possessionFor == Possession.HOME_POSSESSION -> {
                            homeScore++
                        }

                        action.isGoal && action.possessionFor == Possession.AWAY_POSSESSION -> {
                            awayScore++
                        }
                    }
                    val matchInfoResult = matchInfo.copy(
                        results = matchInfo.results?.copy(
                            homeScore = homeScore.toString(),
                            awayScore = awayScore.toString(),
                        ),
                        time = (action.id + 1).toString()
                    )
                    updateMatchInfo(matchInfoResult)
                    delay(200L)
                }
            }.invokeOnCompletion {
                isSimulationPlaying.value = false
            }
        }
    }

    private fun updateMatchInfo(matchInfoResult: MatchInfo) {
        roundState.apply {
            val matches = value?.matches?.map {
                if (it.id == matchInfoResult.id) {
                    matchInfoResult
                } else {
                    it
                }
            }.orEmpty()
            value = value?.copy(
                matches = matches,
            )
        }
    }
}