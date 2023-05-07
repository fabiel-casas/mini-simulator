package com.fabiel.casas.simulator.usecase

import com.fabiel.casas.simulator.ui.screens.rounds.RoundItemState
import com.fabiel.casas.simulator.ui.screens.standings.StandingsItemState
import kotlinx.coroutines.flow.Flow

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
interface MatchesUseCase {

    fun matchesFlow(): Flow<List<RoundItemState>>
    suspend fun createMatches()
    fun roundMatches(roundId: Int): Flow<RoundItemState>
    suspend fun saveMatchSimulation(roundItemState: RoundItemState)
    fun getStandings(): Flow<List<StandingsItemState>>
}