package com.fabiel.casas.simulator.usecase

import com.fabiel.casas.simulator.ui.screens.rounds.MatchInfo
import com.fabiel.casas.simulator.ui.screens.rounds.results.RoundSimulationState
import kotlinx.coroutines.flow.Flow

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
interface SimulatorUseCase {

    suspend fun simulateA(match: MatchInfo): MatchInfo
    suspend fun simulateRound(roundSimulationState: RoundSimulationState): RoundSimulationState
    fun simulateAnimation(matchInfo: MatchInfo, delay: Long): Flow<MatchInfo>
}
