package com.drabatx.animegall.presentation.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.drabatx.animegall.presentation.model.AnimeModel
import com.drabatx.animegall.presentation.view.dialogs.LoadingDialog
import com.drabatx.animegall.presentation.view.item_list.ItemAnimeView
import com.drabatx.animegall.presentation.view.viewmodels.TopAnimViewModel

@Composable
fun TopAnimeScreen(modifier: Modifier, viewModel: TopAnimViewModel) {
    val animes = viewModel.topAnimeList.collectAsLazyPagingItems()

    when {
        animes.loadState.refresh is LoadState.Loading && animes.itemCount == 0 -> {
            LoadingDialog(isLoading = true)
        }
        animes.loadState.refresh is LoadState.NotLoading && animes.itemCount == 0 -> {
            Text(text = "Not Loading")
        }
        animes.loadState.hasError -> {
            Text(text = "Error")
        }
        else -> {
            Scaffold { innerPadding ->
                TopAnimeList(innerPadding = innerPadding, animeList = animes)
            }
            if (animes.loadState.append is LoadState.Loading) {
                LoadingDialog(isLoading = true)
            }
        }
    }
//    if (viewModel.animes.isEmpty()) {
//        LaunchedEffect(Unit) {
//            viewModel.getTopAnimeList()
//        }
//    }
}

@Composable
fun TopAnimeList(innerPadding: PaddingValues, animeList: LazyPagingItems<AnimeModel>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = innerPadding,
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
    val sampleData = listOf(
        AnimeModel(
            name = "Full Metal Alchemist: Brotherhood",
            status = "Completed",
            score = 8.0,
            rank = 123,
            year = 2002,
            image = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg"
        ),
        AnimeModel(
            name = "Diebuster",
            status = "Completed",
            score = 7.5,
            rank = 234,
            year = 2004,
            image = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg"
        )
    )
    ItemAnimeView(animeItem = sampleData[0])
}
