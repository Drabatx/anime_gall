package com.drabatx.animegall.presentation.view.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drabatx.animegall.data.domain.usecase.anime.GetTopAnimeListUseCase
import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import com.drabatx.animegall.presentation.model.AnimeItemList
import com.drabatx.animegall.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAnimViewModel @Inject constructor(private val getTopAnimeUseCas: GetTopAnimeListUseCase) :
    ViewModel() {
    private val _topAnimeStateFlow =
        MutableStateFlow<Result<TopAnimeResponse>>(Result.Loading)
    val topAnimeStateFlow: StateFlow<Result<TopAnimeResponse>> get() = _topAnimeStateFlow

    var animes: List<AnimeItemList> by mutableStateOf(emptyList())

    fun getTopAnimeList() {
        viewModelScope.launch {
            try {
                getTopAnimeUseCas().collect { result ->
                    _topAnimeStateFlow.value = result
                }
            } catch (e: Exception) {
                _topAnimeStateFlow.value =
                    Result.Error(Throwable("Failed load anime list: ${e.message}"))
            }
        }
    }

    fun saveAnime(list: List<AnimeItemList>){
        animes = list
    }
}