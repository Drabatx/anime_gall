package com.drabatx.animegall.data.domain.repository.anime

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drabatx.animegall.data.network.ApiService
import com.drabatx.animegall.presentation.mappers.TopAnimeResponseToAnimeItemListMapper
import com.drabatx.animegall.presentation.model.AnimeModel
import javax.inject.Inject

class AnimePagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, AnimeModel>() {
    override fun getRefreshKey(state: PagingState<Int, AnimeModel>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeModel> {
        return try {
            val page = params.key ?: 1

            val response = apiService.getTopAnime(page)
            val animeList = response.data
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (response.pagination.has_next_page) page + 1 else null

            val animes = animeList.map {
                TopAnimeResponseToAnimeItemListMapper.map(it)
            }
            LoadResult.Page(
                data = animes,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}