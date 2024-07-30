package com.drabatx.animegall.data.domain.repository.anime

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.drabatx.animegall.data.network.ApiService
import com.drabatx.animegall.presentation.mappers.AnimeDetailsResponseToAnimeDetailsModelMapper
import com.drabatx.animegall.presentation.model.AnimeDetailsModel
import com.drabatx.animegall.presentation.model.AnimeModel
import com.drabatx.animegall.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AnimeRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    AnimeRepository {
    override suspend fun getTopAnime(filter: String): Flow<PagingData<AnimeModel>> {
        val animePagingSource = AnimePagingSource(apiService)
        animePagingSource.setFilter(filter)
        return Pager(config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        ),
            pagingSourceFactory = {
                animePagingSource
            }).flow
    }

    override suspend fun findAnimeById(animeId: Int): Flow<Result<AnimeDetailsModel>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.findAnimeById(animeId)
            val animeDetails = AnimeDetailsResponseToAnimeDetailsModelMapper.map(response.data)
            emit(Result.Success(animeDetails))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    companion object {
        private const val PAGE_SIZE = 25
        private const val PREFETCH_DISTANCE = 5
    }
}