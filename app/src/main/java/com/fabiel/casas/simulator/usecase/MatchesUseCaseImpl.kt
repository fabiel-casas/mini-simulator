package com.fabiel.casas.simulator.usecase

import com.fabiel.casas.simulator.model.MiniSimulatorDatabase
import com.fabiel.casas.simulator.model.dao.MatchDao
import com.fabiel.casas.simulator.model.dao.TeamDao
import com.fabiel.casas.simulator.model.table.Match
import com.fabiel.casas.simulator.ui.screens.rounds.MatchInfo
import com.fabiel.casas.simulator.ui.screens.rounds.RoundItemState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
class MatchesUseCaseImpl(
    database: MiniSimulatorDatabase
) : MatchesUseCase {
    private val matchDao: MatchDao = database.getMatch()
    private val teamDao: TeamDao = database.getTeam()
    override fun matchesFlow(): Flow<List<RoundItemState>> {
        return matchDao.getMatchesFlow().map { matches ->
            listOfNotNull(
                matches.roundItem(1),
                matches.roundItem(2),
                matches.roundItem(3),
            )
        }
    }

    private suspend fun List<Match>.roundItem(id: Int): RoundItemState? {
        val list = this.filter { it.roundId == id }
        return if (list.isNotEmpty()) {
            RoundItemState(
                roundId = id,
                matches = list
                    .map { it.toMatchInfo() }
            )
        } else {
            null
        }
    }

    private suspend fun Match.toMatchInfo(): MatchInfo {
        val homeTeam = teamDao.getTeam(homeTeamId)
        val awayTeam = teamDao.getTeam(awayTeamId)
        return MatchInfo(
            id = id,
            homeTeam = homeTeam,
            awayTeam = awayTeam,
            roundId = roundId,
            results = results
        )
    }

    override suspend fun createMatches() {
        val teams = teamDao.getTeams()
            .asSequence()
            .shuffled()
            .take(4)
            .toList()
        matchDao.deleteAll()
        // Round 1
        matchDao.insert(
            Match(
                homeTeamId = teams[0].id,
                awayTeamId = teams[1].id,
                roundId = 1,
                results = null
            )
        )
        matchDao.insert(
            Match(
                homeTeamId = teams[2].id,
                awayTeamId = teams[3].id,
                roundId = 1,
                results = null
            )
        )
        // Round 2
        matchDao.insert(
            Match(
                homeTeamId = teams[2].id,
                awayTeamId = teams[1].id,
                roundId = 2,
                results = null
            )
        )
        matchDao.insert(
            Match(
                homeTeamId = teams[3].id,
                awayTeamId = teams[0].id,
                roundId = 2,
                results = null
            )
        )
        // Round 3
        matchDao.insert(
            Match(
                homeTeamId = teams[0].id,
                awayTeamId = teams[2].id,
                roundId = 3,
                results = null
            )
        )
        matchDao.insert(
            Match(
                homeTeamId = teams[3].id,
                awayTeamId = teams[1].id,
                roundId = 3,
                results = null
            )
        )
    }

    override fun roundMatches(roundId: Int): Flow<RoundItemState> {
        return matchDao.getMatchesByRound(roundId)
            .map { matches ->
                RoundItemState(
                    roundId = roundId,
                    matches = matches.map { it.toMatchInfo() }
                )
            }
    }

    override suspend fun saveMatchSimulation(roundItemState: RoundItemState) {
        roundItemState.matches.forEach { matchInfo ->
            matchDao.insert(
                Match(
                    id = matchInfo.id,
                    homeTeamId = matchInfo.homeTeam.id,
                    awayTeamId = matchInfo.awayTeam.id,
                    roundId = matchInfo.roundId,
                    results = matchInfo.results
                )
            )
        }
    }
}