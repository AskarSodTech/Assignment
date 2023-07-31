package com.androiddevs.movieslistapp.modelview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.movieslistapp.model.Movie
import com.androiddevs.movieslistapp.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get() = _movieList
    private var allMovies: List<Movie> = emptyList()
    private val PAGE_SIZE = 20
    var currentPage = 0
    private var isLoading = false


    init {
        val initialFileName = "content1.json"
        loadNextPage(initialFileName)
    }


    // Check if there are more pages to load
    fun hasMorePages(totalContentItems: Int, pageSize: Int): Boolean {
        return currentPage * pageSize < totalContentItems
    }

    fun loadNextPage(fileName: String) {
        if (isLoading) return

        val fileName = "content${currentPage + 1}.json"
        val totalContentItems = 54
        if (hasMorePages(totalContentItems, PAGE_SIZE)) {
            isLoading = true
            viewModelScope.launch {
                val movies = movieRepository.getMoviesFromAssets(fileName)
                allMovies = allMovies + movies
                _movieList.value = allMovies.take((currentPage + 1) * PAGE_SIZE)
                currentPage++
                isLoading = false

            }
        }
    }

    fun getFilteredMovies(query: String): List<Movie> {
        return allMovies.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun clearSearch() {
        _movieList.value = allMovies.take(PAGE_SIZE)
    }

    companion object {
        const val VISIBLE_THRESHOLD = 5
    }
}

