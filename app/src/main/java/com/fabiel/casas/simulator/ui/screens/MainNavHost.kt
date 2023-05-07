package com.fabiel.casas.simulator.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fabiel.casas.simulator.ui.components.BottomNavigation
import com.fabiel.casas.simulator.ui.screens.rounds.RoundsListScreen
import com.fabiel.casas.simulator.ui.screens.rounds.results.RoundsCalculationScreen
import com.fabiel.casas.simulator.ui.screens.standings.StandingsScreen

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { contentPadding ->
        NavHost(
            modifier = Modifier.padding(contentPadding),
            navController = navController,
            startDestination = NavDestination.Standings.route
        ) {
            composable(NavDestination.Standings.route) {
                StandingsScreen()
            }
            composable(
                route = "${NavDestination.RoundsSimulator.route}/{roundId}",
                arguments = listOf(navArgument("roundId") { type = NavType.StringType })
            ) { backStackEntry ->
                RoundsCalculationScreen(
                    onBackAction = {
                        navController.popBackStack()
                    },
                    roundId = backStackEntry.arguments?.getString("roundId")
                )
            }
            composable(NavDestination.RoundsList.route) {
                RoundsListScreen(
                    onRoundClick = { roundId ->
                        navController.navigate(
                            NavDestination.RoundsSimulator.withPathParameters(
                                roundId.toString()
                            )
                        )
                    }
                )
            }
        }
    }
}