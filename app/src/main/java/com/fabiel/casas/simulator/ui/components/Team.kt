package com.fabiel.casas.simulator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fabiel.casas.simulator.R
import com.fabiel.casas.simulator.ui.theme.MiniSimulatorTheme

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
@Composable
fun TeamItem(
    modifier: Modifier = Modifier,
    logo: String?,
    name: String,
    iconSize: Dp = 32.dp,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    textAlign: TextAlign? = null,
) {
    Column(
        modifier = modifier.widthIn(min = 50.dp, 150.dp),
        horizontalAlignment = horizontalAlignment,
    ) {
        AsyncImage(
            modifier = Modifier.requiredSize(iconSize),
            model = logo,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_team),
            contentScale = ContentScale.Fit,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = textAlign,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TeamItemPreview() {
    MiniSimulatorTheme {
        TeamItem(
            logo = "",
            name = "Team A"
        )
    }
}