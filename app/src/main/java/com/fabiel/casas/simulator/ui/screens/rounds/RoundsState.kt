package com.fabiel.casas.simulator.ui.screens.rounds

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    val homeTeam: TeamInfo,
    val awayTeam: TeamInfo,
    val roundId: Int,
    val results: MatchScore?,
    val time: String = "0",
    val matchTimeLine: List<MatchAction> = emptyList()
)

data class TeamInfo(
    val id: String,
    val name: String,
    val logo: String,
    val attack: Int,
    val defense: Int,
    val possession: Int,
    val recovery: Int,
)

data class MatchScore(
    val homeScore: String = "0",
    val awayScore: String = "0",
    val homeBallPossession: String = "",
    val awayBallPossession: String = "",
    val winnerTeamId: String? = null,
)

data class MatchAction(
    val id: Int,
    val isGoal: Boolean = false,
    val possessionFor: Possession,
)

enum class Possession {
    HOME_POSSESSION,
    AWAY_POSSESSION,
}
