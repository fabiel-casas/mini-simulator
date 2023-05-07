package com.fabiel.casas.simulator.ui.screens.standings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

/**
 * Mingle Sport Tech
 * Created on 07/05/2023.
 */
data class StandingsState(
    val standings: State<List<StandingsItemState>> = mutableStateOf(emptyList())
)

data class StandingsItemState(
    val teamId: String,
    val teamName: String,
    val teamLogo: String,
    val played: String,
    val win: String,
    val draw: String,
    val loss: String,
    val points: String,
    val goalScored: String,
    val goalAgainst: String,
    val goalDifference: String,
)
