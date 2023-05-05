package com.fabiel.casas.simulator.usecase

import com.fabiel.casas.simulator.model.table.Match

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
interface SimulatorUseCase {

    suspend fun simulateA(match: Match): Match
    suspend fun simulateARound(matches: Pair<Match, Match>): Pair<Match, Match>
}