package com.drabatx.animegall.data.domain.repository.anime

import androidx.paging.PagingData
import com.drabatx.animegall.presentation.model.AnimeModel
import com.drabatx.animegall.presentation.model.AnimeDetailsModel
import com.drabatx.animegall.utils.Result
import kotlinx.coroutines.flow.Flow


interface AnimeRepository {
    suspend fun getTopAnime(filter: String): Flow<PagingData<AnimeModel>>
    suspend fun findAnimeById(animeId: Int):  Flow<Result<AnimeDetailsModel>>
}