package com.codery.atheneum.ui.main.genre

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.codery.atheneum.databinding.FragmentGenreViewerBinding
import com.manavtamboli.axion.binding.BindingFragment


class GenreViewerFragment : BindingFragment<FragmentGenreViewerBinding>(FragmentGenreViewerBinding::class.java){

    val args by navArgs<GenreViewerFragmentArgs>()

    private val viewModel by viewModels<GenreViewerViewModel>{ GenreViewerViewModelFactory(requireActivity().application,args.catGenre.id)}

    override fun FragmentGenreViewerBinding.initialize() {
    // Initialization Logic

    }
}