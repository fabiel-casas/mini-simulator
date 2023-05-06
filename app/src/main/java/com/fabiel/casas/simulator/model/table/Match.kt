package com.fabiel.casas.simulator.model.table

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
@Entity
data class Match(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val homeTeamId: String,
    val awayTeamId: String,
    val roundId: Int,
    @Embedded val results: MatchResults?,
)

data class MatchResults(
    val homeScore: Int,
    val awayScore: Int,
    val homeBallPossession: Int,
    val awayBallPossession: Int,
    val winnerTeamId: String?,
)