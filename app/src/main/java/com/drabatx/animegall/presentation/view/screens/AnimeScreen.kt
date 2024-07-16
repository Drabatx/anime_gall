package com.drabatx.animegall.presentation.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.drabatx.animegall.R
import com.drabatx.animegall.presentation.model.AnimeModel
import com.drabatx.animegall.presentation.model.sampleData
import com.drabatx.animegall.presentation.view.dialogs.LoadingDialog
import com.drabatx.animegall.presentation.view.dialogs.MessageDialog
import com.drabatx.animegall.presentation.view.item_list.ItemAnimeView
import com.drabatx.animegall.presentation.view.theme.margin_small
import com.drabatx.animegall.presentation.view.theme.margin_xsmall
import com.drabatx.animegall.presentation.view.utils.AnimeFilter
import com.drabatx.animegall.presentation.view.viewmodels.TopAnimViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreen(viewModel: TopAnimViewModel, navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                androidx.wear.compose.material.Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.offset(x = margin_small),
                    tint = MaterialTheme.colorScheme.onPrimary // Ajusta el color del icono si es necesario
                )
            }

        )
    }, content = { padding ->
        Column { // Usamos Column para organizar los composables
            FilterChip(
                viewModel = viewModel,
                paddingValues = padding
            ) // Mostramos el FilterChip
            AnimeList(padding, viewModel) // Luego mostramos la lista
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterChip(viewModel: TopAnimViewModel, paddingValues: PaddingValues) {
    val currentFilter = viewModel.currentFilter.collectAsState()
    FlowRow(
        modifier = Modifier.padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(margin_xsmall),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        AnimeFilter.entries.forEach { filter ->
            ElevatedFilterChip(
                selected = currentFilter.value == filter,
                onClick = {
                    if (currentFilter.value != filter) viewModel.setFilter(filter)
                },
                label = { Text(text = filter.text) },
            )
        }
    }
}

@Composable
private fun AnimeList(innerPadding: PaddingValues, viewModel: TopAnimViewModel) {
    val animes = viewModel.topAnimeList.collectAsLazyPagingItems()
    when {
        animes.loadState.refresh is LoadState.Loading && animes.itemCount == 0 -> {
            LoadingDialog(isLoading = true)
        }

        animes.loadState.refresh is LoadState.NotLoading && animes.itemCount == 0 -> {
            MessageDialog(
                title = stringResource(R.string.no_results),
                text = stringResource(R.string.no_results_please_retry_later),
                showDialog = true,
                secondaryButtonText = stringResource(R.string.retry),
                onSecondaryButton = { animes.retry() }
            )
        }

        animes.loadState.hasError -> {
            val errorState = animes.loadState.refresh as? LoadState.Error
            MessageDialog(
                title = stringResource(R.string.error),
                text = errorState?.error?.message ?: stringResource(R.string.error_to_load_data),
                showDialog = true,
                secondaryButtonText = stringResource(R.string.retry),
                onSecondaryButton = { animes.retry() }
            )
        }

        else -> {
            TopAnimeList(innerPadding, animes)
            if (animes.loadState.append is LoadState.Loading) {
                LoadingDialog(isLoading = true)
            }
        }
    }
}

@Composable
fun TopAnimeList(innerPadding: PaddingValues, animeList: LazyPagingItems<AnimeModel>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
//        contentPadding = innerPadding,
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(animeList.itemCount) { index ->
            animeList[index]?.let { animeModel ->
                ItemAnimeView(animeModel)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAnimeItemListGrid() {
    ItemAnimeView(animeItem = sampleData[0])
}


