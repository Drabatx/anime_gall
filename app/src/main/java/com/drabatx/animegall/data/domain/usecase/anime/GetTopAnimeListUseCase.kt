package com.drabatx.animegall.data.domain.usecase.anime

import androidx.paging.PagingData
import com.drabatx.animegall.data.domain.repository.anime.AnimeRepository
import com.drabatx.animegall.presentation.model.AnimeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopAnimeListUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(filter: String): Flow<PagingData<AnimeModel>> {
        return repository.getTopAnime(filter) // Pasa el filtro al repositorio
    }
}