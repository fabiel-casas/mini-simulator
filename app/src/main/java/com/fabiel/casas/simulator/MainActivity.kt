package com.fabiel.casas.simulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fabiel.casas.simulator.model.table.Match
import com.fabiel.casas.simulator.model.table.Team
import com.fabiel.casas.simulator.ui.screens.MainNavHost
import com.fabiel.casas.simulator.ui.theme.MiniSimulatorTheme
import com.fabiel.casas.simulator.usecase.SimulatorUseCase
import com.fabiel.casas.simulator.usecase.SimulatorUseCaseImpl

class MainActivity : ComponentActivity() {

    private val simulatorUseCase: SimulatorUseCase = SimulatorUseCaseImpl()
    private val teamOne = Team(
        name = "Team One",
        attack = 50,
        defense = 50,
        possession = 50,
        recovery = 50
    )
    private val teamTwo = Team(
        name = "Team Two",
        attack = 50,
        defense = 50,
        possession = 50,
        recovery = 50
    )
    private val match = Match(
        homeTeam = teamOne,
        awayTeam = teamTwo,
        results = null
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniSimulatorTheme {
                MainNavHost()
            }
        }
    }
}
