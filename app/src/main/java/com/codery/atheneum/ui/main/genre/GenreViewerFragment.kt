package com.codery.atheneum.ui.main.genre

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import coil.load
import com.codery.atheneum.data.repos.AllBooksRepo
import com.codery.atheneum.databinding.FragmentGenreViewerBinding
import com.codery.atheneum.databinding.ItemAllbooksBinding
import com.codery.atheneum.models.Book
import com.codery.atheneum.models.Genre
import com.codery.atheneum.ui.main.MainViewModel
import com.manavtamboli.axion.binding.AxionAdapter
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.AxionFactory
import com.manavtamboli.axion.lifecycle.flows
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map


class GenreViewerFragment : BindingFragment<FragmentGenreViewerBinding>(FragmentGenreViewerBinding::class.java){

    private val args by navArgs<GenreViewerFragmentArgs>()

    private val mainViewModel : MainViewModel by activityViewModels()
    private val viewModel : GenreViewModel by viewModels { AxionFactory<GenreViewModel, Genre, AllBooksRepo>(args.genre, mainViewModel.booksRepo) }

    private val adapter by AxionAdapter(ItemAllbooksBinding::class.java, Book){
        onBind {
            txtBookname.text = it.name
            txtAuthor.text = it.author
            itemBookImage.load(it.image)
        }
    }

    init {
        flows {
            collectFlow(viewModel.filteredBooks){
                adapter.submitList(it)
            }
        }
    }

    override fun FragmentGenreViewerBinding.initialize() {
        genreBookList.adapter = adapter
        genreName.text = args.genre.name
        genreSearch.doAfterTextChanged {
            viewModel.updateQuery(it?.toString() ?: "")
        }
    }
}

class GenreViewModel(genre : Genre, booksRepo: AllBooksRepo) : ViewModel(){

    private val _query = MutableStateFlow("")

    private val booksByGenre = booksRepo.allBooks.map {
        it.filter { book ->
            genre in book.genres
        }
    }
    val filteredBooks = _query.combine(booksByGenre){ filter, books ->
        books
            .filter {
                it.name.contains(filter, true)
            }
            .sortedBy {
                it.name.indexOf(filter, ignoreCase = true)
            }
    }

    fun updateQuery(newQuery : String) {
        _query.value = newQuery.trim()
    }
}