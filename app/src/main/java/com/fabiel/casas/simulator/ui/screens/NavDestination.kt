package com.fabiel.casas.simulator.ui.screens

import com.fabiel.casas.simulator.R

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
sealed class NavDestination(val route: String) {

    object RoundsSimulator : NavDestination("rounds_simulator")
    object RoundsList : NavDestination("rounds_list")
    object Standings : NavDestination("standings")

    companion object {
        fun navigationList() = listOf(Standings, RoundsList)
    }
}

fun NavDestination.bottomName(): Int? = when (this) {
    NavDestination.RoundsList -> R.string.bottom_bar_rounds
    NavDestination.Standings -> R.string.bottom_bar_standings
    else -> null
}

fun NavDestination.bottomIcon(): Int? = when (this) {
    NavDestination.RoundsList -> R.drawable.ic_fixtures
    NavDestination.Standings -> R.drawable.ic_footbal
    else -> null
}