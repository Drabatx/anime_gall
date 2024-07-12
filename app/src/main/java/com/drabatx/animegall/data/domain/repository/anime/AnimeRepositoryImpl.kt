package com.drabatx.animegall.data.domain.repository.anime

import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import com.drabatx.animegall.data.network.ApiService
import com.drabatx.animegall.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AnimeRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    AnimeRepository {
    override suspend fun getTopAnime(): Flow<Result<TopAnimeResponse>> = flow {
        try {
            val response = apiService.getTopAnime(1)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}