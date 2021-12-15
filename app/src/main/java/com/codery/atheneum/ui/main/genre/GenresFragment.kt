package com.codery.atheneum.ui.main.genre

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.codery.atheneum.data.repos.AllGenresRepo
import com.codery.atheneum.databinding.FragmentGenresBinding
import com.codery.atheneum.databinding.ItemGenreSearchBinding
import com.codery.atheneum.models.Genre
import com.codery.atheneum.ui.main.MainViewModel
import com.manavtamboli.axion.binding.AxionAdapter
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.AxionFactory
import com.manavtamboli.axion.lifecycle.flows
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class GenresFragment : BindingFragment<FragmentGenresBinding>(FragmentGenresBinding::class.java){

    private val mainViewModel : MainViewModel by activityViewModels()
    private val viewModel : GenresViewModel by viewModels { AxionFactory<GenresViewModel, AllGenresRepo>(mainViewModel.genresRepo) }

    private val adapter by AxionAdapter(ItemGenreSearchBinding::class.java, Genre.Companion.Diff){
        onBind {
            txtSeachItem.text = it.name
        }
        onItemClick {
            mainViewModel.navigate(GenresFragmentDirections.genresToGenre(it))
        }
    }

    init {
        flows {
            collectFlow(viewModel.filteredGenres){
                adapter.submitList(it)
            }
        }
    }

    override fun FragmentGenresBinding.initialize() {
        recyclerGenreSearch.adapter = adapter
        genresSearch.doAfterTextChanged {
            viewModel.updateQuery(it?.toString() ?: "")
        }
    }
}

class GenresViewModel(genresRepo: AllGenresRepo) : ViewModel(){

    private val _query = MutableStateFlow("")

    val filteredGenres = _query.combine(genresRepo.allGenres){ filter, genres ->
        genres
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