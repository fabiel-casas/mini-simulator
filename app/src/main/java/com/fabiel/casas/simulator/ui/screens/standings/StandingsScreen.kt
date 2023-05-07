package com.fabiel.casas.simulator.ui.screens.standings

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fabiel.casas.simulator.R
import com.fabiel.casas.simulator.ui.theme.MiniSimulatorTheme
import org.koin.androidx.compose.getViewModel

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandingsScreen(
    viewModels: StandingsViewModels = getViewModel(),
) {
    val state = viewModels.state
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.bottom_bar_standings)
                    )
                }
            )
        }
    ) { contentPadding ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            StandingsTable(state)
        }
    }
}

@Composable
private fun RowScope.StandingsTable(state: StandingsState) {
    Column(
        modifier = Modifier
            .weight(0.5f)
            .padding(start = 16.dp)
    ) {
        Text(text = stringResource(R.string.standings_table_field_club))
        Divider()
        state.standings.value.forEachIndexed { index, standingsItemState ->
            Row(
                modifier = Modifier.requiredHeight(45.dp),
                verticalAlignment = CenterVertically,
            ) {
                Text(text = "${index + 1}")
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(standingsItemState.teamLogo)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_team),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .requiredSize(24.dp)
                )
                Text(
                    text = standingsItemState.teamName,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Divider()
        }
    }
    Column(
        modifier = Modifier.Companion
            .weight(0.5f)
            .horizontalScroll(rememberScrollState())
    ) {
        StandingsTableFields(
            modifier = Modifier.width(40.dp),
        )
        state.standings.value.forEach { standingsItemState ->
            StandingsFields(
                modifier = Modifier
                    .width(40.dp),
                standingsItemState = standingsItemState
            )
        }
    }
}

@Composable
private fun StandingsFields(
    modifier: Modifier,
    standingsItemState: StandingsItemState,
) {
    Row {
        TableRowValue(
            modifier = modifier,
            text = standingsItemState.played
        )
        TableRowValue(
            modifier = modifier,
            text = standingsItemState.win
        )
        TableRowValue(
            modifier = modifier,
            text = standingsItemState.draw
        )
        TableRowValue(
            modifier = modifier,
            text = standingsItemState.loss
        )
        TableRowValue(
            modifier = modifier,
            text = standingsItemState.points,
            fontWeight = FontWeight.Bold,
        )
        TableRowValue(
            modifier = modifier,
            text = standingsItemState.goalScored
        )
        TableRowValue(
            modifier = modifier,
            text = standingsItemState.goalAgainst
        )
        TableRowValue(
            modifier = modifier,
            text = standingsItemState.goalDifference
        )
    }
    Divider()
}

@Composable
private fun TableRowValue(
    modifier: Modifier,
    text: String,
    fontWeight: FontWeight? = null,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.requiredHeight(45.dp),
            verticalAlignment = CenterVertically,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Center,
                fontWeight = fontWeight
            )
        }
    }
}

@Composable
private fun StandingsTableFields(
    modifier: Modifier
) {
    Row {
        TableFieldName(
            modifier = modifier,
            text = stringResource(R.string.standings_table_field_mp)
        )
        TableFieldName(
            modifier = modifier,
            text = stringResource(R.string.standings_table_field_w)
        )
        TableFieldName(
            modifier = modifier,
            text = stringResource(R.string.standings_table_field_d)
        )
        TableFieldName(
            modifier = modifier,
            text = stringResource(R.string.standings_table_field_l)
        )
        TableFieldName(
            modifier = modifier,
            text = stringResource(R.string.standings_table_field_pts),
            fontWeight = FontWeight.Bold,
        )
        TableFieldName(
            modifier = modifier,
            text = stringResource(R.string.standings_table_field_gf)
        )
        TableFieldName(
            modifier = modifier,
            text = stringResource(R.string.standings_table_field_ga)
        )
        TableFieldName(
            modifier = modifier,
            text = stringResource(R.string.standings_table_field_gd)
        )
    }
}

@Composable
private fun TableFieldName(
    modifier: Modifier,
    text: String,
    fontWeight: FontWeight? = null,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = fontWeight
        )
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
private fun StandingTablePreview() {
    MiniSimulatorTheme {
        Row {
            StandingsTable(state = StandingsState())
        }
    }
}