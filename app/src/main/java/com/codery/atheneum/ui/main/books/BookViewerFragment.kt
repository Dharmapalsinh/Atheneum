package com.codery.atheneum.ui.main.books

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.codery.atheneum.databinding.FragmentBookViewerBinding
import com.codery.atheneum.databinding.ItemGenreBinding
import com.codery.atheneum.models.Genre
import com.codery.atheneum.ui.main.MainViewModel
import com.codery.atheneum.ui.main.dashboard.home.GenreSearchAdapter
import com.codery.atheneum.ui.main.genre.DataViewModel
import com.manavtamboli.axion.binding.AxionAdapter
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.flows

class BookViewerFragment : BindingFragment<FragmentBookViewerBinding>(FragmentBookViewerBinding::class.java){

    private val args by navArgs<BookViewerFragmentArgs>()
    private val viewModel : MainViewModel by activityViewModels()

    private val adapter by AxionAdapter(ItemGenreBinding::class.java, Genre.Companion.Diff){
        onBind {
            itemGenreName.text = it.name
        }
    }

    init {
        flows {
            collectFlow(viewModel.bookById(args.book.id)){
                it ?: return@collectFlow
                binding.availBookViewerName.text = it.name
                binding.availBookViewerAuthor.text = it.author
                binding.imageView6.load(it.image)
                binding.bookLength.text = it.length.toString()
                binding.bookPublisher.text = it.publisher
                binding.bookDesc.text = it.desc
                adapter.submitList(it.genres)
            }
        }
    }

    override fun FragmentBookViewerBinding.initialize() {
        imgArrowBackViewer.setOnClickListener {
            requireActivity().onBackPressed()
        }
        recyclerBookViewerAvail.adapter = adapter
    }
}