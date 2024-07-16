package com.drabatx.animegall.data.domain.usecase.anime

import com.drabatx.animegall.data.domain.repository.anime.AnimeRepository
import com.drabatx.animegall.presentation.model.FullAnimeModel
import com.drabatx.animegall.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeId: Int): Flow<Result<FullAnimeModel>> {
        return repository.findAnimeById(animeId) // Pasa el filtro al repositorio
    }
}