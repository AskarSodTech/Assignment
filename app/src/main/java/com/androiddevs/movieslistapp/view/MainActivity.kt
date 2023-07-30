package com.androiddevs.movieslistapp.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.movieslistapp.MyApplication
import com.androiddevs.movieslistapp.adapter.MovieAdapter
import com.androiddevs.movieslistapp.databinding.ActivityMainBinding
import com.androiddevs.movieslistapp.modelview.MovieRepository
import com.androiddevs.movieslistapp.modelview.MovieViewModel
import com.androiddevs.movieslistapp.modelview.MovieViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private val pageSize = 20
    private var isLoading = false
    private var isSearchMode = false
    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory


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

        setupSearchView()
        loadNextPage()
    }



    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                isSearchMode = if (newText.length >= 3) {
                    val filteredMovies = movieViewModel.getFilteredMovies(newText)
                    movieAdapter.updateData(filteredMovies)
                    true
                } else {
                    // Clear the search and show the original list
                    movieAdapter.updateData(movieViewModel.movieList.value.orEmpty())
                    false
                }
                return true
            }
        })

        binding.searchView.setOnCloseListener {
            // Clear the search and show the original list
            movieAdapter.updateData(movieViewModel.movieList.value.orEmpty())
            isSearchMode = false
            false
        }
    }

    /***
     * Method to check orientation
     */
    private fun isPortrait(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * Method to load json from asset folder
     */
    private fun loadNextPage() {
        if (isLoading) return

        val fileName = "content${movieViewModel.currentPage + 1}.json"
        val totalContentItems = 54 // Total number of items in all pages (you can retrieve this from the JSON)
        if (movieViewModel.hasMorePages(totalContentItems, pageSize)) {
            isLoading = true
            binding.progressBar.visibility = View.VISIBLE
            movieViewModel.loadNextPage(fileName)

            isLoading = false
            binding.progressBar.visibility = View.GONE

        }
    }

    /***
     *
     */
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

            if (!isLoading && totalItemCount <= (lastVisibleItem + MovieViewModel.VISIBLE_THRESHOLD)) {
                loadNextPage()
            }
        }
    }
}
