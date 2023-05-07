package com.fabiel.casas.simulator.ui.screens

import android.net.Uri
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
        fun navigationList() = listOf(RoundsList, Standings)
    }
}

fun NavDestination.withPathParameters(vararg params: String): String {
    val uri = Uri.Builder()
    uri.appendPath(route)
    params.forEach {
        uri.appendPath(it)
    }
    val path = uri.toString()
    return path.substring(1, path.length)
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