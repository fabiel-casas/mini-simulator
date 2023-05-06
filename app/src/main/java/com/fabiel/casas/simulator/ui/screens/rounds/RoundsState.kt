package com.fabiel.casas.simulator.ui.screens.rounds

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.fabiel.casas.simulator.model.table.MatchResults
import com.fabiel.casas.simulator.model.table.Team
import java.util.UUID

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
data class RoundsState(
    val roundList: State<List<RoundItemState>> = mutableStateOf(emptyList()),
    val isCreateMatchesVisible: State<Boolean> = mutableStateOf(false),
)

data class RoundItemState(
    val roundId: Int,
    val matches: List<MatchInfo>,
)

data class MatchInfo(
    val id: String = UUID.randomUUID().toString(),
    val homeTeam: Team,
    val awayTeam: Team,
    val roundId: Int,
    val results: MatchResults?,
)
