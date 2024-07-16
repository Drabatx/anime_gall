package com.drabatx.animegall.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drabatx.animegall.data.domain.usecase.anime.GetAnimeByIdUseCase
import com.drabatx.animegall.presentation.model.FullAnimeModel
import com.drabatx.animegall.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeByIdViewModel @Inject constructor(private val getAnimeByIdUseCase: GetAnimeByIdUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<Result<FullAnimeModel>>(Result.Initial)
    val uiState: StateFlow<Result<FullAnimeModel>> = _uiState.asStateFlow()

    fun getAnimeById(animeId: Int) {
        viewModelScope.launch {
            _uiState.value = Result.Loading
            try {
                getAnimeByIdUseCase(animeId).collect { result ->
                    _uiState.value = result
                }
            } catch (e: Exception) {
                _uiState.value = Result.Error(e)
            }
        }
    }
}