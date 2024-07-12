package com.drabatx.animegall.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.drabatx.animegall.data.domain.usecase.anime.GetTopAnimeListUseCase
import com.drabatx.animegall.presentation.model.AnimeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TopAnimViewModel @Inject constructor(private val getTopAnimeUseCas: GetTopAnimeListUseCase) :
    ViewModel() {
    val topAnimeList: Flow<PagingData<AnimeModel>> = flow {
        emitAll(getTopAnimeUseCas())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    )
}