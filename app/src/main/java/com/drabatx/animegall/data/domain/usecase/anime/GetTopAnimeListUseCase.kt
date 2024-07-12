package com.drabatx.animegall.data.domain.usecase.anime

import com.drabatx.animegall.data.domain.repository.anime.AnimeRepository
import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import com.drabatx.animegall.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopAnimeListUseCase @Inject constructor(private val repository: AnimeRepository){
    suspend operator fun invoke(): Flow<Result<TopAnimeResponse>> {
        return repository.getTopAnime()
    }
}