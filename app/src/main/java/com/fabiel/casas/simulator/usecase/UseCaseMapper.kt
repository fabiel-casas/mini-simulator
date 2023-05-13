package com.fabiel.casas.simulator.usecase

import com.fabiel.casas.simulator.model.table.Match
import com.fabiel.casas.simulator.model.table.MatchResults
import com.fabiel.casas.simulator.model.table.Team
import com.fabiel.casas.simulator.ui.screens.rounds.MatchScore
import com.fabiel.casas.simulator.ui.screens.rounds.TeamInfo
import com.fabiel.casas.simulator.ui.screens.standings.StandingsItemState

/**
 * Mingle Sport Tech
 * Created on 07/05/2023.
 */
class UseCaseMapper {

    fun Team.toTeamInfo() = TeamInfo(
        id = id,
        name = name,
        logo = logo,
        attack = attack,
        defense = defense,
        possession = possession,
        recovery = recovery
    )

    fun MatchResults.toMatchScore() = MatchScore(
        homeScore = homeScore.toString(),
        awayScore = awayScore.toString(),
        homeBallPossession = homeBallPossession.toString(),
        awayBallPossession = awayBallPossession.toString(),
        winnerTeamId = winnerTeamId
    )

    fun MatchScore.toMatchResults() = MatchResults(
        homeScore = homeScore.toInt(),
        awayScore = awayScore.toInt(),
        homeBallPossession = homeBallPossession.toInt(),
        awayBallPossession = awayBallPossession.toInt(),
        winnerTeamId = winnerTeamId
    )

    fun List<Team>.toStandingsItemStateList(
        matches: List<Match>,
    ) = this.map { team ->
        val myMatches =
            matches.filter { it.homeTeamId == team.id || it.awayTeamId == team.id }
        val matchPlayed = myMatches.filter { it.results != null }
        val matchWin = myMatches.count { it.results?.winnerTeamId == team.id }
        val matchDraw =
            myMatches.count { it.results != null && it.results.winnerTeamId == null }
        val matchLoss = myMatches.count { it.results != null && it.results.winnerTeamId != team.id }
        val goalsScored = getGoalsScored(matchPlayed, team)
        val goalsAgainst = getGoalsAgainst(matchPlayed, team)
        StandingsItemState(
            teamId = team.id,
            teamName = team.name,
            teamLogo = team.logo,
            played = matchPlayed.size.toString(),
            win = matchWin.toString(),
            draw = matchDraw.toString(),
            loss = matchLoss.toString(),
            points = "${(matchWin * 3) + (matchDraw)}",
            goalScored = goalsScored.toString(),
            goalAgainst = goalsAgainst.toString(),
            goalDifference = "${goalsScored - goalsAgainst}"
        )
    }

    private fun getGoalsAgainst(
        matchPlayed: List<Match>,
        team: Team
    ) = matchPlayed.sumOf {
        if (team.id == it.homeTeamId) {
            it.results?.awayScore ?: 0
        } else {
            it.results?.homeScore ?: 0
        }
    }

    private fun getGoalsScored(
        matchPlayed: List<Match>,
        team: Team
    ) = matchPlayed.sumOf {
        if (team.id == it.homeTeamId) {
            it.results?.homeScore ?: 0
        } else {
            it.results?.awayScore ?: 0
        }
    }
}