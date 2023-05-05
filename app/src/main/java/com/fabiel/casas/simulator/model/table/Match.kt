package com.fabiel.casas.simulator.model.table

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
data class Match(
    val homeTeam: Team,
    val awayTeam: Team,
    val results: MatchResults?,
)

data class MatchResults(
    val homeScore: Int,
    val awayScore: Int,
    val homeBallPossession: Int,
    val awayBallPossession: Int,
)