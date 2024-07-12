package com.drabatx.animegall.data.domain.repository.anime

import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import com.drabatx.animegall.utils.Result
import kotlinx.coroutines.flow.Flow


interface AnimeRepository {
    suspend fun getTopAnime(): Flow<Result<TopAnimeResponse>>
}