package com.androiddevs.movieslistapp.view

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.movieslistapp.adapter.TitleAdapter
import com.androiddevs.movieslistapp.modelview.MovieViewModel
import com.androiddevs.mvvmnewsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TitleAdapter
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViews()
        initDailog()
        initListners()
    }

    /***
     * Method to init listeners for LiveData
     */
    private fun initListners() {

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                movieViewModel.searchMovies(query)

            }
        }

        movieViewModel.getMoviesLiveData().observe(this, { movies ->
            adapter.submitList(movies)
        })

        movieViewModel.getLoadingLiveData().observe(this, { isLoading ->
            if (isLoading) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })
    }

    /***
     * Method to initialize views
     *
     */
    private fun initializeViews() {
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        adapter = TitleAdapter(emptyList())
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun initDailog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
    }



}
