package com.drabatx.animegall.presentation.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.drabatx.animegall.R
import com.drabatx.animegall.presentation.model.AnimeModel
import com.drabatx.animegall.presentation.view.dialogs.LoadingDialog
import com.drabatx.animegall.presentation.view.dialogs.MessageDialog
import com.drabatx.animegall.presentation.view.item_list.ItemAnimeView
import com.drabatx.animegall.presentation.view.theme.margin_xsmall
import com.drabatx.animegall.presentation.view.utils.AnimeFilter
import com.drabatx.animegall.presentation.view.viewmodels.TopAnimViewModel
import com.drabatx.animegall.presentation.view.widgets.MainTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreen(viewModel: TopAnimViewModel, navController: NavController) {
    Scaffold(topBar = {
        MainTopBar(navController = navController)
    }, content = { padding ->
        Column { // Usamos Column para organizar los composables
            FilterChip(
                viewModel = viewModel,
                paddingValues = padding
            ) // Mostramos el FilterChip
            AnimeList(viewModel, navController) // Luego mostramos la lista
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
private fun AnimeList(viewModel: TopAnimViewModel, navController: NavController) {
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
            TopAnimeList(animes, navController)
            if (animes.loadState.append is LoadState.Loading) {
                LoadingDialog(isLoading = true)
            }
        }
    }
}

@Composable
fun TopAnimeList(animeList: LazyPagingItems<AnimeModel>, navController: NavController) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(animeList.itemCount) { index ->
            animeList[index]?.let { animeModel ->
                ItemAnimeView(animeItem = animeModel, navController = navController)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAnimeItemListGrid() {
//    ItemAnimeView(animeItem = sampleData[0]))
}


