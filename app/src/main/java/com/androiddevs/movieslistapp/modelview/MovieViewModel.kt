package com.androiddevs.movieslistapp.modelview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.movieslistapp.model.Result
import com.androiddevs.movieslistapp.model.Title
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()
    private val moviesLiveData = MutableLiveData<List<Result>>()

    fun getMoviesLiveData(): LiveData<List<Result>> {
        return moviesLiveData
    }

    fun searchMovies(query: String) {
        isLoadingLiveData.value = true
        viewModelScope.launch {
            try {
                movieRepository.getMovies(query).enqueue(object : Callback<Title> {
                    override fun onResponse(call: Call<Title>, response: Response<Title>) {
                        isLoadingLiveData.value = false
                        if (response.isSuccessful) {
                            val title = response.body()
                            moviesLiveData.value = title?.results
                        } else {
                            // Handle error
                        }
                    }

                    override fun onFailure(call: Call<Title>, t: Throwable) {
                        isLoadingLiveData.value = false
                        // Handle failure
                    }
                })
            } catch (e: Exception) {
                // Handle failure
            }
        }
    }

    private val isLoadingLiveData = MutableLiveData<Boolean>()

    fun getLoadingLiveData(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}
