package com.example.pruebatecnica.ui.movies.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.FragmentPopularMoviesBinding
import com.example.pruebatecnica.domain.getPopularMovies.GetMoviesStatus
import com.example.pruebatecnica.domain.model.MovieItem
import com.example.pruebatecnica.ui.movies.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private val popularMoviesViewModel: PopularMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        executeService()
        return binding.root
    }

    private fun initRecyclerView(_listMovies: List<MovieItem>) {
        val recyclerView = _binding!!.rvPopularMovies
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = MovieAdapter(_listMovies.toMutableList())
    }

    private fun executeService() {
        popularMoviesViewModel.getAllPopularMovies(1) {
            when(it) {
                GetMoviesStatus.Loading -> binding.progressBar.visibility = View.VISIBLE
                GetMoviesStatus.Done -> binding.progressBar.visibility = View.INVISIBLE
                GetMoviesStatus.EmptyList -> {
                    findNavController().navigate(R.id.action_navigation_movies_to_emptyListDialog)
                    //showDialog(type = DialogType.INFORMATION, title = "", message = "")
                }
                is GetMoviesStatus.Failure -> Toast.makeText(requireContext(), "Error, try again", Toast.LENGTH_SHORT).show()
                is GetMoviesStatus.Success -> initRecyclerView(it.listMovies)
            }
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
