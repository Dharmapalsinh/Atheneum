package com.codery.atheneum.ui.main.genre

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codery.atheneum.databinding.FragmentGenresBinding
import com.codery.atheneum.ui.main.dashboard.home.GenreSearchAdapter
import com.codery.atheneum.ui.main.dashboard.home.GenreSearchViewModel
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.viewLifecycleLazy

class GenresFragment : BindingFragment<FragmentGenresBinding>(FragmentGenresBinding::class.java){

    private val viewModel by viewModels<GenreSearchViewModel>()

    private val adapter = GenreSearchAdapter {
        findNavController().navigate(GenresFragmentDirections.genresToGenre(it))
    }

    override fun FragmentGenresBinding.initialize() {
        // Initialization Logic
        recyclerGenreSearch.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.genres.observe(viewLifecycleOwner) {
            if(it == null) return@observe
            adapter.submitList(it)
        }
    }
}