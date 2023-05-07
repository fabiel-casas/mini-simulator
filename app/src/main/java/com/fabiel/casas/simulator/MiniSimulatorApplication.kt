package com.fabiel.casas.simulator

import android.app.Application
import com.fabiel.casas.simulator.model.MiniSimulatorDatabase
import com.fabiel.casas.simulator.ui.screens.rounds.RoundListViewModel
import com.fabiel.casas.simulator.ui.screens.rounds.results.RoundsCalculationViewModel
import com.fabiel.casas.simulator.ui.screens.standings.StandingsViewModels
import com.fabiel.casas.simulator.usecase.MatchesUseCase
import com.fabiel.casas.simulator.usecase.MatchesUseCaseImpl
import com.fabiel.casas.simulator.usecase.SimulatorUseCase
import com.fabiel.casas.simulator.usecase.SimulatorUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
class MiniSimulatorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MiniSimulatorApplication)
            modules(mainModule)
        }
    }
}

val mainModule = module {
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
    single {
        MiniSimulatorDatabase.buildDatabase(get(), get())
    }
    factory<SimulatorUseCase> {
        SimulatorUseCaseImpl()
    }
    factory<MatchesUseCase> {
        MatchesUseCaseImpl(get())
    }
    viewModel {
        RoundsCalculationViewModel(get(), get())
    }
    viewModel {
        RoundListViewModel(get())
    }
    viewModel {
        StandingsViewModels(get())
    }
}