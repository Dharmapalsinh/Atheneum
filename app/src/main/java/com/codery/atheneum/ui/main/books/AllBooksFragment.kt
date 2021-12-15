package com.codery.atheneum.ui.main.books

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import coil.load
import com.codery.atheneum.data.repos.AllBooksRepo
import com.codery.atheneum.databinding.FragmentAllBooks2Binding
import com.codery.atheneum.databinding.ItemAllbooksBinding
import com.codery.atheneum.models.Book
import com.codery.atheneum.ui.main.MainViewModel
import com.manavtamboli.axion.binding.AxionAdapter
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.AxionFactory
import com.manavtamboli.axion.lifecycle.flows
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class AllBooksFragment : BindingFragment<FragmentAllBooks2Binding>(FragmentAllBooks2Binding::class.java) {

    private val mainViewModel : MainViewModel by activityViewModels()
    private val viewModel : BooksViewModel by viewModels { AxionFactory<BooksViewModel, AllBooksRepo>(mainViewModel.booksRepo) }

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


    override fun FragmentAllBooks2Binding.initialize() {
        allBooksRecycler.adapter = adapter
        booksSearch.doAfterTextChanged {
            viewModel.updateQuery(it?.toString() ?: "")
        }
    }
}

class BooksViewModel(booksRepo: AllBooksRepo) : ViewModel(){

    private val _query = MutableStateFlow("")

    val filteredBooks = _query.combine(booksRepo.allBooks){ filter, books ->
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