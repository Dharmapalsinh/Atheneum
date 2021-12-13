package com.codery.atheneum.ui.main.books

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.codery.atheneum.databinding.FragmentBookViewerBinding
import com.codery.atheneum.ui.main.dashboard.home.GenreSearchAdapter
import com.codery.atheneum.ui.main.genre.DataViewModel
import com.manavtamboli.axion.binding.BindingFragment

class BookViewerFragment : BindingFragment<FragmentBookViewerBinding>(FragmentBookViewerBinding::class.java){

    val args by navArgs<BookViewerFragmentArgs>()
    val viewModel : DataViewModel by viewModels()

    private val genresAapter = GenreSearchAdapter() {

    }

    override fun FragmentBookViewerBinding.initialize() {
        imgArrowBackViewer.setOnClickListener {
            requireActivity().onBackPressed()
        }

        availBookViewerName.text = args.book.newlyBook
        availBookViewerAuthor.text =args.book.newlyAuthor
    }
}