package com.fabiel.casas.simulator.ui.components

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fabiel.casas.simulator.ui.screens.NavDestination
import com.fabiel.casas.simulator.ui.screens.bottomIcon
import com.fabiel.casas.simulator.ui.screens.bottomName

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
@Composable
fun BottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    if (NavDestination.navigationList().firstOrNull { it.route == currentRoute } == null) return
    NavigationBar {
        NavDestination.navigationList().forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    item.bottomIcon()?.let { iconRes ->
                        Icon(
                            modifier = Modifier.requiredSize(24.dp),
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                        )
                    }
                },
                label = {
                    item.bottomName()?.let { stringRes ->
                        Text(
                            text = stringResource(id = stringRes),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            )
        }
    }
}