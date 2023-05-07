package com.fabiel.casas.simulator.usecase

import com.fabiel.casas.simulator.ui.screens.rounds.RoundItemState
import com.fabiel.casas.simulator.ui.screens.rounds.results.RoundSimulationState
import com.fabiel.casas.simulator.ui.screens.standings.StandingsItemState
import kotlinx.coroutines.flow.Flow

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
interface MatchesUseCase {

    fun matchesFlow(): Flow<List<RoundItemState>>
    suspend fun createRoundsMatches()
    suspend fun roundMatches(roundId: Int): RoundSimulationState
    suspend fun saveMatchSimulation(roundSimulationState: RoundSimulationState)
    fun getStandings(): Flow<List<StandingsItemState>>
}