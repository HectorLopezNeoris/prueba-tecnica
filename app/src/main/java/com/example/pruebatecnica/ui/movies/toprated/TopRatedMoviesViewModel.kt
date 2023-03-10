package com.example.pruebatecnica.ui.movies.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.domain.GetTopRatedMovies
import com.example.pruebatecnica.domain.getPopularMovies.GetMoviesError
import com.example.pruebatecnica.domain.getPopularMovies.GetMoviesStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val getTopRatedMovies: GetTopRatedMovies
) : ViewModel() {

    fun getAllTopRatedMovies(uiState: (GetMoviesStatus) -> Unit) {
        uiState(GetMoviesStatus.Loading)
        try {
            viewModelScope.launch {
                val result = getTopRatedMovies()
                uiState(GetMoviesStatus.Done)
                if(!result.isNullOrEmpty()) {
                    uiState(GetMoviesStatus.Success(result))
                } else {
                    uiState(GetMoviesStatus.EmptyList)
                }
            }
        } catch (e: Exception) {
            uiState(GetMoviesStatus.Done)
            uiState(GetMoviesStatus.Failure(GetMoviesError.DEFAULT_ERROR))
        }
    }
}