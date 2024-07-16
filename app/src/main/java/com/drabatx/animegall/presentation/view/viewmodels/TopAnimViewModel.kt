package com.drabatx.animegall.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.drabatx.animegall.data.domain.usecase.anime.GetTopAnimeListUseCase
import com.drabatx.animegall.presentation.model.AnimeModel
import com.drabatx.animegall.presentation.view.utils.AnimeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAnimViewModel @Inject constructor(private val getTopAnimeUseCas: GetTopAnimeListUseCase) :
    ViewModel() {

    private val _currentFilter = MutableStateFlow(AnimeFilter.TOP)
    val currentFilter = _currentFilter.asStateFlow()

    val topAnimeList: Flow<PagingData<AnimeModel>> = _currentFilter.flatMapLatest { filter ->
        getTopAnimeUseCas(filter.filter) // Pasa el filtro al UseCase
    }.cachedIn(viewModelScope)

    fun setFilter(filter: AnimeFilter) {
        viewModelScope.launch {
            _currentFilter.value = filter
            getTopAnimeUseCas(filter.filter)
        }
    }
}