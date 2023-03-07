package com.example.pruebatecnica.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pruebatecnica.ui.movies.popular.PopularMoviesFragment
import com.example.pruebatecnica.ui.movies.toprated.TopRatedMoviesFragment

class ViewPagerMoviesAdapter(
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PopularMoviesFragment()
            1 -> TopRatedMoviesFragment()
            else -> PopularMoviesFragment()
        }
    }
}