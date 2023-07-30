package com.androiddevs.movieslistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.movieslistapp.R
import com.androiddevs.movieslistapp.databinding.ItemMovieBinding
import com.androiddevs.movieslistapp.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: List<Movie> = emptyList()

    fun updateData(newData: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MovieDiffCallback(movieList, newData))
        movieList = newData // Update the movieList with the new data
        diffResult.dispatchUpdatesTo(this)
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
            binding.movieNameTextView.text = movie.getTruncatedName(20)
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.placeholder_for_missing_posters) // Placeholder image resource
                .error(R.drawable.placeholder_for_missing_posters)
            Glide.with(binding.root)
                .load(getImageResourceId(movie.posterImage))
                .apply(requestOptions)
                .into(binding.moviePosterImageView)
        }

        private fun getImageResourceId(imageName: String): Int {
            if (imageName == null || imageName.isEmpty() || imageName == "posterthatismissing.jpg") {
                return R.drawable.placeholder_for_missing_posters
            }
            val context = binding.root.context
            return context.resources.getIdentifier(imageName.substringBeforeLast("."), "drawable", context.packageName)
        }
    }

    private class MovieDiffCallback(private val oldList: List<Movie>, private val newList: List<Movie>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
