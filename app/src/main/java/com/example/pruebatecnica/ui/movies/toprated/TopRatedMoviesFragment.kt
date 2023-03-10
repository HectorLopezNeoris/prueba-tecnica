package com.example.pruebatecnica.ui.movies.toprated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.FragmentTopRatedMoviesBinding
import com.example.pruebatecnica.domain.getPopularMovies.GetMoviesStatus
import com.example.pruebatecnica.domain.model.MovieItem
import com.example.pruebatecnica.ui.movies.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment() {

    private var _binding: FragmentTopRatedMoviesBinding? = null
    private val binding get() = _binding!!

    private val topRatedMoviesViewModel: TopRatedMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)
        executeService()
        return binding.root
    }

    private fun initRecyclerView(_listMovies: List<MovieItem>) {
        val recyclerView = _binding!!.rvTopRatedMovies
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        val movieAdapter: MovieAdapter = MovieAdapter()
        movieAdapter.submitList(_listMovies)
        recyclerView.adapter = movieAdapter
    }

    private fun executeService() {
        topRatedMoviesViewModel.getAllTopRatedMovies() {
            when(it) {
                GetMoviesStatus.Loading -> binding.progressBar.visibility = View.VISIBLE
                GetMoviesStatus.Done -> binding.progressBar.visibility = View.INVISIBLE
                GetMoviesStatus.EmptyList ->
                    findNavController().navigate(R.id.action_navigation_movies_to_emptyListDialog)
                is GetMoviesStatus.Failure ->
                    findNavController().navigate(R.id.action_navigation_movies_to_emptyListDialog)
                is GetMoviesStatus.Success -> initRecyclerView(it.listMovies)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}