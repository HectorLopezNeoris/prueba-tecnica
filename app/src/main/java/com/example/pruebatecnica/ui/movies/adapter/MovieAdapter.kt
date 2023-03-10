package com.example.pruebatecnica.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.ItemMovieBinding
import com.example.pruebatecnica.domain.model.MovieItem


class MovieAdapter() : ListAdapter<MovieItem, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    object DIFF_CALLBACK: DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.equals(newItem)
        }
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemMovieBinding.bind(view)

        fun onBind(movieItem: MovieItem) {
            Glide.with(binding.ivPoster.context)
                .load(movieItem.posterPath)
                .error(R.drawable.ic_logo_tmdb)
                .placeholder(R.drawable.ic_logo_tmdb)
                .into(binding.ivPoster)
        }
    }
}