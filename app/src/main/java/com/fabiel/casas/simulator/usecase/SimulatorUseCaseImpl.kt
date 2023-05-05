package com.fabiel.casas.simulator.usecase

import android.util.Log
import com.fabiel.casas.simulator.model.table.Match
import com.fabiel.casas.simulator.model.table.MatchResults
import kotlin.random.Random

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
const val totalMinutes = 90

class SimulatorUseCaseImpl : SimulatorUseCase {

    override suspend fun simulateARound(matches: Pair<Match, Match>): Pair<Match, Match> {
        return Pair(
            simulateA(match = matches.first),
            simulateA(match = matches.second)
        )
    }

    override suspend fun simulateA(match: Match): Match {
        val matchActions = mutableListOf<MatchAction>()
        var previousMatchAction: MatchAction? = null
        repeat(totalMinutes) { minute ->
            val matchAction = runMatchAction(
                minute = minute,
                match = match,
                possessionFor = previousMatchAction.nextPossessionFor(minute = minute)
            )
            previousMatchAction = matchAction
            matchActions.add(matchAction)
        }
        val result = matchActions.toMatchResults()
        Log.d("*** Match Results", "Match results = $result")
        return match.copy(results = result)
    }

    private fun MatchAction?.nextPossessionFor(minute: Int): Possession {
        return when {
            minute == 46 -> { // start a second half
                Possession.AWAY_POSSESSION
            }

            this == null -> {
                Possession.HOME_POSSESSION
            } // the match just started
            isGoal && possessionFor == Possession.HOME_POSSESSION -> { // goal for home
                Possession.AWAY_POSSESSION
            }

            isGoal && possessionFor == Possession.AWAY_POSSESSION -> { // goal for away
                Possession.HOME_POSSESSION
            }

            else -> possessionFor
        }
    }

    private fun List<MatchAction>.toMatchResults(): MatchResults {
        val homeGoals = count { it.isGoal && it.possessionFor == Possession.HOME_POSSESSION }
        val awayGoals = count { it.isGoal && it.possessionFor == Possession.AWAY_POSSESSION }
        val homeBallPossession =
            count { it.possessionFor == Possession.HOME_POSSESSION } / totalMinutes
        val awayBallPossession =
            count { it.possessionFor == Possession.AWAY_POSSESSION } / totalMinutes
        return MatchResults(
            homeScore = homeGoals,
            awayScore = awayGoals,
            homeBallPossession = homeBallPossession,
            awayBallPossession = awayBallPossession
        )
    }

    private fun runMatchAction(
        minute: Int,
        match: Match,
        possessionFor: Possession,
    ): MatchAction {
        //define team possession
        val currentPossession =
            definePossession(match = match, previousPossession = possessionFor)
        var isGoal = false
        if (possessionFor == currentPossession) {
            //attack time
            isGoal = attachTime(match = match, possession = currentPossession)
        }
        return MatchAction(
            id = minute,
            isGoal = isGoal,
            possessionFor = currentPossession
        )
    }

    private fun attachTime(match: Match, possession: Possession): Boolean {
        return when (possession) {
            Possession.HOME_POSSESSION -> match.homeAttack()
            Possession.AWAY_POSSESSION -> match.awayAttack()
        }
    }

    private fun definePossession(
        match: Match,
        previousPossession: Possession,
    ): Possession {
        return when (previousPossession) {
            Possession.HOME_POSSESSION -> {
                val homeBallPossessionProbability = Random.nextInt(0, match.homeTeam.possession)
                val awayBallRecoveryProbability = Random.nextInt(0, match.awayTeam.recovery)
                Log.d(
                    "*** Possession",
                    "AP=$homeBallPossessionProbability, HR=$awayBallRecoveryProbability, keep=${homeBallPossessionProbability > awayBallRecoveryProbability}"
                )
                if (homeBallPossessionProbability > awayBallRecoveryProbability) {
                    Possession.HOME_POSSESSION
                } else {
                    Possession.AWAY_POSSESSION
                }
            }

            Possession.AWAY_POSSESSION -> {
                val awayBallPossessionProbability = Random.nextInt(0, match.awayTeam.possession)
                val homeBallRecoveryProbability = Random.nextInt(0, match.homeTeam.recovery)
                Log.d(
                    "*** Possession",
                    "AP=$awayBallPossessionProbability, HR=$homeBallRecoveryProbability, keep=${awayBallPossessionProbability > homeBallRecoveryProbability}"
                )
                if (awayBallPossessionProbability > homeBallRecoveryProbability) {
                    Possession.AWAY_POSSESSION
                } else {
                    Possession.HOME_POSSESSION
                }
            }
        }
    }

    private fun Match.homeAttack(): Boolean {
        val homeScoreProbability = Random.nextInt(0, homeTeam.attack)
        val awayDefenceProbability = Random.nextInt(0, awayTeam.defense)
        Log.d(
            "*** Attach",
            "HA=$homeScoreProbability, AD=$awayDefenceProbability, goal=${homeScoreProbability > awayDefenceProbability}"
        )
        return homeScoreProbability > awayDefenceProbability
    }

    private fun Match.awayAttack(): Boolean {
        val awayScoreProbability = Random.nextInt(0, awayTeam.attack)
        val homeDefenceProbability = Random.nextInt(0, homeTeam.defense)
        Log.d(
            "*** Attach",
            "HA=$awayScoreProbability, AD=$homeDefenceProbability, goal=${awayScoreProbability > homeDefenceProbability}"
        )
        return awayScoreProbability > homeDefenceProbability
    }
}

data class MatchAction(
    val id: Int,
    val isGoal: Boolean = false,
    val possessionFor: Possession,
)

enum class Possession {
    HOME_POSSESSION,
    AWAY_POSSESSION,
}