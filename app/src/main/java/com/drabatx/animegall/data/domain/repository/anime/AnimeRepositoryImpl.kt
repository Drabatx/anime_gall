package com.drabatx.animegall.data.domain.repository.anime

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.drabatx.animegall.data.network.ApiService
import com.drabatx.animegall.presentation.model.AnimeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AnimeRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    AnimeRepository {
    override suspend fun getTopAnime(): Flow<PagingData<AnimeModel>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = {
                AnimePagingSource(apiService)
            }).flow
    }

    companion object {
        private const val PAGE_SIZE = 25
        private const val PREFETCH_DISTANCE = 5
    }
    //    override suspend fun getTopAnime(): Flow<Result<TopAnimeResponse>> = flow {
//        try {
//            val response = apiService.getTopAnime(1)
//            emit(Result.Success(response))
//        } catch (e: Exception) {
//            emit(Result.Error(e))
//        }
//    }
}