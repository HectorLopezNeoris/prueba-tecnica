package com.example.pruebatecnica.ui.movies.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.domain.GetPopularMovies
import com.example.pruebatecnica.domain.GetTopRatedMovies
import com.example.pruebatecnica.domain.getPopularMovies.GetMoviesError
import com.example.pruebatecnica.domain.getPopularMovies.GetMoviesStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    fun getAllPopularMovies(page: Int, uiState: (GetMoviesStatus) -> Unit) {
        uiState(GetMoviesStatus.Loading)
        try {
            viewModelScope.launch {
                val result = getPopularMovies(page)
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