package com.drabatx.animegall.data.domain.repository.anime

import androidx.paging.PagingData
import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import com.drabatx.animegall.presentation.model.AnimeModel
import com.drabatx.animegall.utils.Result
import kotlinx.coroutines.flow.Flow


interface AnimeRepository {
    suspend fun getTopAnime(): Flow<PagingData<AnimeModel>>
}