package com.androiddevs.movieslistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.movieslistapp.model.Result
import com.androiddevs.movieslistapp.R
import com.androiddevs.movieslistapp.databinding.ItemTitleBinding

class TitleAdapter(private var titles: List<Result>) :
    RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemTitleBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_title, parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val title = titles.get(position)
        holder.bind(title)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class TitleViewHolder(private val binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: Result) {
            binding.title = title
            binding.executePendingBindings()
        }
    }

    fun submitList(newTitles: List<Result>) {
        titles = newTitles
        notifyDataSetChanged()
    }


}
