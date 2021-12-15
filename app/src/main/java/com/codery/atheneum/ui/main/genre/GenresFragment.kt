package com.codery.atheneum.ui.main.genre

import androidx.fragment.app.activityViewModels
import com.codery.atheneum.databinding.FragmentGenresBinding
import com.codery.atheneum.databinding.ItemGenreSearchBinding
import com.codery.atheneum.models.Genre
import com.codery.atheneum.ui.main.MainViewModel
import com.manavtamboli.axion.binding.AxionAdapter
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.flows

class GenresFragment : BindingFragment<FragmentGenresBinding>(FragmentGenresBinding::class.java){

    private val mainViewModel : MainViewModel by activityViewModels()

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
            collectFlow(mainViewModel.allGenres){
                adapter.submitList(it)
            }
        }
    }

    override fun FragmentGenresBinding.initialize() {
        recyclerGenreSearch.adapter = adapter
    }
}