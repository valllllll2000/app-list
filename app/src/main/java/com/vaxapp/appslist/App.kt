package com.vaxapp.appslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun App(viewModel: AppListViewModel) {
    viewModel.state.value.let { state ->
        when (state) {
            is AppUIState.AppUIStateReady -> Box(contentAlignment = Alignment.Center) {
                AppsList(state.apps)
            }

            AppUIState.Loading -> Progress()
        }
    }
}

@Composable
fun AppsList(apps: List<InstalledApp>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(apps) { app: InstalledApp ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {
                        Text(
                            app.name,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Row {
                        Text(app.packageName, modifier = Modifier.weight(1f))
                    }
                    Row {
                        Text(
                            app.version,
                            modifier = Modifier.weight(1f),
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Progress() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CircularProgressIndicator()
    }
}
