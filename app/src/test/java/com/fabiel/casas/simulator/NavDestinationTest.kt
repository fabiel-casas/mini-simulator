package com.fabiel.casas.simulator

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fabiel.casas.simulator.ui.screens.NavDestination
import com.fabiel.casas.simulator.ui.screens.withPathParameters
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
@Config(
    sdk = [Build.VERSION_CODES.P]
)
@RunWith(AndroidJUnit4::class)
class NavDestinationTest {

    @Test
    fun withPathParameters_givenTwoParams_returnAPath() {
        val result = NavDestination.RoundsSimulator.withPathParameters("1", "4")
        Truth.assertThat(result).isEqualTo("rounds_simulator/1/4")
    }
}