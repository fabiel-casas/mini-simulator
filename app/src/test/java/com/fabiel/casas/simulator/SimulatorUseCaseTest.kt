package com.fabiel.casas.simulator

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fabiel.casas.simulator.model.table.Team
import com.fabiel.casas.simulator.ui.screens.rounds.MatchInfo
import com.fabiel.casas.simulator.usecase.SimulatorUseCase
import com.fabiel.casas.simulator.usecase.SimulatorUseCaseImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.annotation.Config

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Config(
    sdk = [Build.VERSION_CODES.P]
)
@RunWith(AndroidJUnit4::class)
class SimulatorUseCaseTest : KoinTest {

    private val simulatorUseCase: SimulatorUseCase by inject()
    private val scope = TestScope()
    private val teamOne = Team(
        name = "Team One",
        logo = "Team One",
        attack = 10,
        defense = 30,
        possession = 70,
        recovery = 30
    )
    private val teamTwo = Team(
        name = "Team Two",
        logo = "Team Two",
        attack = 10,
        defense = 50,
        possession = 50,
        recovery = 50
    )
    private val match = MatchInfo(
        homeTeam = teamOne,
        awayTeam = teamTwo,
        roundId = 1,
        results = null
    )

    @Before
    fun start() {
        startKoin {
            modules(modules = module {
                factory<SimulatorUseCase> { SimulatorUseCaseImpl() }
            })
        }
    }

    @After
    fun clean() {
        stopKoin()
    }

    @Test
    fun runSimulation_whenGivenCorrectMatch_returnAMatchWithResults() {
        scope.runTest {
            val matchResult1 = simulatorUseCase.simulateA(match = match)
            val matchResult2 = simulatorUseCase.simulateA(match = match)
            Truth.assertThat(matchResult1.results).isNotNull()
            Truth.assertThat(matchResult2.results).isNotNull()
            Truth.assertThat(matchResult1).isNotEqualTo(matchResult2)
        }
    }
}