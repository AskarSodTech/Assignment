package com.androiddevs.movieslistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.movieslistapp.R
import com.androiddevs.movieslistapp.databinding.ItemMovieBinding
import com.androiddevs.movieslistapp.model.Movie
import com.bumptech.glide.Glide

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: List<Movie> = emptyList()

    fun updateData(newData: List<Movie>) {
        movieList = movieList + newData
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }


    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieNameTextView.text = movie.name
            binding.moviePosterImageView.setImageDrawable(binding.root.context.resources.getDrawable(
                R.drawable.poster1))
          /*  Glide.with(binding.root)
                .load(getImageResourceId(movie.posterImage))
                .into(binding.moviePosterImageView)*/
        }

        private fun getImageResourceId(imageName: String): Int {
            val context = binding.root.context
            return context.resources.getIdentifier(imageName, "drawable", context.packageName)
        }
    }
}
