package com.drabatx.animegall.data.domain.repository.anime

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drabatx.animegall.data.network.ApiService
import com.drabatx.animegall.presentation.mappers.TopAnimeResponseToAnimeItemListMapper
import com.drabatx.animegall.presentation.model.AnimeModel
import com.drabatx.animegall.presentation.view.utils.AnimeFilter
import javax.inject.Inject

class AnimePagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, AnimeModel>() {
    var currentFilter: String = AnimeFilter.TOP.filter // Ahora es una propiedad pública

    fun setFilter(filter: String) {
        currentFilter = filter
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeModel>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeModel> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getTopAnime(page, filter = currentFilter)
            val animeList = response.data
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (response.pagination.has_next_page) page + 1 else null

            val animes = animeList.map {
                TopAnimeResponseToAnimeItemListMapper.map(it, currentFilter)
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