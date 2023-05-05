package com.fabiel.casas.simulator

import android.app.Application
import com.fabiel.casas.simulator.ui.screens.rounds.results.RoundsCalculationViewModel
import com.fabiel.casas.simulator.usecase.SimulatorUseCase
import com.fabiel.casas.simulator.usecase.SimulatorUseCaseImpl
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
    factory<SimulatorUseCase> {
        SimulatorUseCaseImpl()
    }
    viewModel {
        RoundsCalculationViewModel(get())
    }
}