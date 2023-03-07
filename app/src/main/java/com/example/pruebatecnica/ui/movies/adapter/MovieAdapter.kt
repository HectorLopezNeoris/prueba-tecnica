package com.example.pruebatecnica.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.ItemMovieBinding
import com.example.pruebatecnica.domain.model.MovieItem


class MovieAdapter(
    private val moviesList: MutableList<MovieItem>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = moviesList[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = moviesList.size


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