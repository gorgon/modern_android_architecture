package com.example.myapplication.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    @Composable
    fun ListItemComponent(item: String) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = item,
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp
            )
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Rows Demo") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.processIntent(MainIntent.AddItem) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Row")
            }
        }
    ) { innerPadding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding),
//            contentAlignment = Alignment.Center
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is MainViewState.Loading -> CircularProgressIndicator()
                is MainViewState.Content -> {
                    val items = (state as MainViewState.Content).items
                    if (items.isEmpty()) {
                        Text("No items, click the '+' button to add.")
                    } else {
                        LazyColumn {
                            items(items) { item ->
                                ListItemComponent(item)
                            }
                        }
                    }
                }
                is MainViewState.Error -> {
                    Text(
                        text = (state as MainViewState.Error).error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
