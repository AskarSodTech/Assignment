package com.androiddevs.movieslistapp.modelview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.movieslistapp.model.ContentItems
import com.androiddevs.movieslistapp.model.Movie
import com.androiddevs.movieslistapp.model.Page
import com.androiddevs.movieslistapp.model.Result
import com.androiddevs.movieslistapp.model.Title
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get() = _movieList

    var currentPage = 0

    fun loadNextPage(fileName: String) {
        val movies = movieRepository.getMoviesFromAssets(fileName)
        _movieList.value = _movieList.value?.plus(movies) ?: movies
        currentPage++
    }

    fun hasMorePages(totalContentItems: Int, pageSize: Int): Boolean {
        return currentPage * pageSize < totalContentItems
    }
}
