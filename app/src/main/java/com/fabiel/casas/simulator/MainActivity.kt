package com.fabiel.casas.simulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fabiel.casas.simulator.ui.screens.MainNavHost
import com.fabiel.casas.simulator.ui.theme.MiniSimulatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniSimulatorTheme {
                MainNavHost()
            }
        }
    }
}
