package com.drabatx.animegall.presentation.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import com.drabatx.animegall.presentation.mappers.TopAnimeResponseToAnimeItemListMapper
import com.drabatx.animegall.presentation.model.AnimeItemList
import com.drabatx.animegall.presentation.view.item_list.ItemAnimeView
import com.drabatx.animegall.presentation.view.viewmodels.TopAnimViewModel
import com.drabatx.animegall.utils.Result

@Composable
fun TopAnimeScreen(modifier: Modifier, viewModel: TopAnimViewModel) {
    val topAnimeState by viewModel.topAnimeStateFlow.collectAsState(initial = Result.Loading)
    Scaffold { innerPadding ->
        when (topAnimeState) {
            is Result.Initial -> {
                Log.d("DEBUG", "Initial")
            }

            is Result.Loading -> {
                Log.d("DEBUG", "Loading")
            }

            is Result.Error -> {
                val errorMessage = (topAnimeState as Result.Error).exception.message
                Log.d("DEBUG", "Error: $errorMessage")
            }

            is Result.Success -> {
                Log.d("DEBUG", "Success")
                val response = (topAnimeState as Result.Success<TopAnimeResponse>).data
                val topAnime = response.data.map {
                    TopAnimeResponseToAnimeItemListMapper.map(it)
                }
                viewModel.saveAnime(topAnime)
                TopAnimeList(innerPadding = innerPadding, animeList = topAnime)
            }
        }
    }

    if (viewModel.animes.isEmpty()) {
        LaunchedEffect(Unit) {
            viewModel.getTopAnimeList()
        }
    }
}

@Composable
fun TopAnimeList(innerPadding: PaddingValues, animeList: List<AnimeItemList>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = innerPadding,
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(animeList.size) { index ->
            ItemAnimeView(animeList[index])
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAnimeItemListGrid() {
    val sampleData = listOf(
        AnimeItemList(
            name = "Full Metal Alchemist: Brotherhood",
            status = "Completed",
            score = 8.0,
            rank = 123,
            year = 2002,
            image = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg"
        ),
        AnimeItemList(
            name = "Diebuster",
            status = "Completed",
            score = 7.5,
            rank = 234,
            year = 2004,
            image = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg"
        )
    )
    TopAnimeList(innerPadding = PaddingValues(all = 0.dp), animeList = sampleData)
}
