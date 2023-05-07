package com.fabiel.casas.simulator.ui.screens.rounds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fabiel.casas.simulator.R
import com.fabiel.casas.simulator.model.table.Team
import com.fabiel.casas.simulator.ui.components.TeamItem
import com.fabiel.casas.simulator.ui.theme.MiniSimulatorTheme
import org.koin.androidx.compose.getViewModel

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundsListScreen(
    viewModel: RoundListViewModel = getViewModel(),
    onRoundClick: (Int) -> Unit,
) {
    val state = viewModel.state
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.bottom_bar_rounds)
                    )
                }
            )
        },
        floatingActionButton = {
            if (state.isCreateMatchesVisible.value) {
                ExtendedFloatingActionButton(onClick = { viewModel.createMatches() }) {
                    Text(text = "Start")
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null
                    )
                }
            }
        }
    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding,
            content = {
                items(state.roundList.value) {
                    RoundItem(it, onRoundClick)
                }
            }
        )
    }
}

@Composable
private fun RoundItem(
    roundItemState: RoundItemState,
    onRoundClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.clickable { onRoundClick(roundItemState.roundId) }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.roundList_round_number, roundItemState.roundId),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
        roundItemState.matches.forEach { matchInfo ->
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = SpaceBetween,
                ) {
                    TeamItem(
                        logo = matchInfo.homeTeam.logo,
                        name = matchInfo.homeTeam.name,
                        horizontalAlignment = Start,
                    )
                    TeamItem(
                        logo = matchInfo.awayTeam.logo,
                        name = matchInfo.awayTeam.name,
                        horizontalAlignment = End,
                        textAlign = TextAlign.End,
                    )
                }
                val result = if (matchInfo.results != null) {
                    "${matchInfo.results.homeScore} - ${matchInfo.results.awayScore}"
                } else {
                    "VS"
                }
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = result,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoundItemPreview() {
    val roundItem = remember {
        RoundItemState(
            roundId = 1,
            matches = listOf(
                MatchInfo(
                    id = "",
                    homeTeam = Team(
                        id = "",
                        name = "Team A With a long name 12324",
                        logo = "",
                        attack = 0,
                        defense = 0,
                        possession = 0,
                        recovery = 0
                    ),
                    awayTeam = Team(
                        id = "",
                        name = "Team B  With a long name 12324",
                        logo = "",
                        attack = 0,
                        defense = 0,
                        possession = 0,
                        recovery = 0
                    ),
                    roundId = 0,
                    results = MatchScore(
                        homeScore = "1",
                        awayScore = "0",
                        homeBallPossession = "30",
                        awayBallPossession = "40",
                        winnerTeamId = ""
                    )
                )
            )
        )
    }
    MiniSimulatorTheme {
        RoundItem(
            roundItemState = roundItem,
            onRoundClick = {}
        )
    }
}