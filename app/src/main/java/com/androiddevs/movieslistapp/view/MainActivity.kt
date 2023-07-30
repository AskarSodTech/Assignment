package com.androiddevs.movieslistapp.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.movieslistapp.adapter.MovieAdapter
import com.androiddevs.movieslistapp.databinding.ActivityMainBinding
import com.androiddevs.movieslistapp.modelview.MovieRepository
import com.androiddevs.movieslistapp.modelview.MovieViewModel
import com.androiddevs.movieslistapp.modelview.MovieViewModelFactory


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private val pageSize = 20
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieRepository = MovieRepository(applicationContext)
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(movieRepository))
            .get(MovieViewModel::class.java)

        movieAdapter = MovieAdapter()
        // Set the appropriate grid span count based on the device orientation

        binding.recyclerView.apply {
            val spanCount = if (isPortrait()) 3 else 7
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = movieAdapter
            addOnScrollListener(onScrollListener)
        }
        // Observe LiveData from ViewModel
        movieViewModel.movieList.observe(this) { movies ->
            movieAdapter.updateData(movies)
        }
        loadNextPage()
    }

    private fun isPortrait(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    private fun loadNextPage() {
        if (isLoading) return

        val fileName = "content${movieViewModel.currentPage + 1}.json"
        val totalContentItems = 54 // Total number of items in all pages (you can retrieve this from the JSON)
        if (movieViewModel.hasMorePages(totalContentItems, pageSize)) {
            isLoading = true
            movieViewModel.loadNextPage(fileName)

            // Observe LiveData from ViewModel for the newly loaded data
            movieViewModel.movieList.observe(this) { movies ->
                movieAdapter.updateData(movies)
            }

            isLoading = false
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

            if (!isLoading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                loadNextPage()
            }
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
}
