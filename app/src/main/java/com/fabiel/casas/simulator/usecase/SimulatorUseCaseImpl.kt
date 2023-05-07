package com.fabiel.casas.simulator.usecase

import com.fabiel.casas.simulator.ui.screens.rounds.MatchInfo
import com.fabiel.casas.simulator.ui.screens.rounds.Possession
import com.fabiel.casas.simulator.ui.screens.rounds.results.RoundSimulationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */

class SimulatorUseCaseImpl : SimulatorUseCase {

    private val simulationLogic = SimulationLogic()

    override suspend fun simulateRound(roundSimulationState: RoundSimulationState): RoundSimulationState {
        return RoundSimulationState(
            roundId = roundSimulationState.roundId,
            matches = roundSimulationState.matches.map { simulateA(it) }
        )
    }

    override suspend fun simulateA(match: MatchInfo): MatchInfo {
        return simulationLogic.simulate(match = match)
    }

    override fun simulateAnimation(matchInfo: MatchInfo, delay: Long): Flow<MatchInfo> {
        var homeScore = 0
        var awayScore = 0
        return flow {
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
                emit(matchInfoResult)
                delay(delay)
            }
        }
    }

}