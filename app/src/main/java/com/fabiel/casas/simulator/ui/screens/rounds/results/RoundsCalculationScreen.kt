package com.fabiel.casas.simulator.ui.screens.rounds.results

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fabiel.casas.simulator.R
import com.fabiel.casas.simulator.ui.components.TeamItem
import com.fabiel.casas.simulator.ui.screens.rounds.MatchInfo
import org.koin.androidx.compose.getViewModel

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundsCalculationScreen(
    onBackAction: () -> Unit,
    viewModel: RoundsCalculationViewModel = getViewModel(),
    roundId: String?,
) {
    LaunchedEffect(key1 = roundId, block = {
        viewModel.loadRound(roundId)
    })
    val state = viewModel.state
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.roundList_round_number,
                            roundId.toString()
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackAction) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (state.isPlaySimulationEnable.value) {
                SimulateButton(
                    onClick = {
                        viewModel.startSimulation()
                    }
                )
            }
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier,
            contentPadding = contentPadding,
        ) {
            items(state.roundState.value?.matches.orEmpty()) { matchInfo ->
                val time = remember(
                    state.isSimulationPlaying.value,
                    state.isPlaySimulationEnable.value,
                    matchInfo.time
                ) {
                    when {
                        state.isSimulationPlaying.value -> {
                            "${matchInfo.time}'"
                        }

                        state.isPlaySimulationEnable.value -> {
                            "0'"
                        }

                        else -> {
                            "End"
                        }
                    }
                }
                MatchRoundItem(
                    matchInfo = matchInfo,
                    time = time,
                )
            }
        }
    }
}

@Composable
private fun MatchRoundItem(
    time: String,
    matchInfo: MatchInfo,
) {
    Column {
        Card(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                text = time
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TeamItem(
                    logo = matchInfo.homeTeam.logo,
                    name = matchInfo.homeTeam.name,
                    iconSize = 80.dp,
                    textAlign = TextAlign.Center,
                )
                TeamItem(
                    logo = matchInfo.awayTeam.logo,
                    name = matchInfo.awayTeam.name,
                    iconSize = 80.dp,
                    textAlign = TextAlign.Center,
                )
            }
            Row(modifier = Modifier.align(Alignment.Center)) {
                TextCounter(count = matchInfo.results?.homeScore ?: "0")
                Text(
                    text = " - ",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                TextCounter(count = matchInfo.results?.awayScore ?: "0")
            }
        }
    }
    Divider()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TextCounter(count: String) {
    AnimatedContent(
        targetState = count,
        transitionSpec = {
            // Compare the incoming number with the previous number.
            if (targetState > initialState) {
                // If the target number is larger, it slides up and fades in
                // while the initial (smaller) number slides up and fades out.
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                // If the target number is smaller, it slides down and fades in
                // while the initial number slides down and fades out.
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height } + fadeOut()
            }.using(
                // Disable clipping since the faded slide-in/out should
                // be displayed out of bounds.
                SizeTransform(clip = false)
            )
        }
    ) { targetCount ->
        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            text = targetCount,
        )
    }
}

@Composable
private fun SimulateButton(
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        FloatingActionButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier.requiredSize(48.dp),
                imageVector = Icons.Default.PlayArrow,
                contentDescription = stringResource(
                    R.string.simulator_start_round_simulation
                )
            )
        }
    }
}