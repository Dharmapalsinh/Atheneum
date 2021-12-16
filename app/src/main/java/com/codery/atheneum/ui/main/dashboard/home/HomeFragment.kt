package com.codery.atheneum.ui.main.dashboard.home

import androidx.fragment.app.activityViewModels
import coil.load
import com.codery.atheneum.databinding.FragmentHomeBinding
import com.codery.atheneum.databinding.ItemGenreBinding
import com.codery.atheneum.databinding.ItemNewlyCatalogueBinding
import com.codery.atheneum.models.Book
import com.codery.atheneum.models.Genre
import com.codery.atheneum.ui.main.MainViewModel
import com.codery.atheneum.ui.main.dashboard.DashboardFragmentDirections
import com.manavtamboli.axion.binding.AxionAdapter
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.flows

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::class.java){

    private val mainViewModel : MainViewModel by activityViewModels()

    private val genreAdapter by AxionAdapter(ItemGenreBinding::class.java, Genre.Companion.Diff){
        onBind {
            itemGenreName.text = it.name
        }
        onItemClick {
            mainViewModel.navigate(DashboardFragmentDirections.viewGenre(it))
        }
    }

    private val booksAdapter by AxionAdapter(ItemNewlyCatalogueBinding::class.java, Book){
        onBind {
            txtNewlyBookName.text = it.name
            txtNewlyAuthor.text = it.author
            bookImg.load(it.image)
        }
        onItemClick {
            mainViewModel.navigate(DashboardFragmentDirections.viewBook(it))
        }
    }

    init {
        flows {
            collectFlow(mainViewModel.topGenres){
                genreAdapter.submitList(it)
            }
            collectFlow(mainViewModel.newlyAddedBooks){
                booksAdapter.submitList(it)
            }
        }
    }

    override fun FragmentHomeBinding.initialize() {
        txtCatAllGenre.setOnClickListener {
            mainViewModel.navigate(DashboardFragmentDirections.viewAllGenres())
        }
        txtCatAllBooks.setOnClickListener {
            mainViewModel.navigate(DashboardFragmentDirections.viewAllBooks())
        }

        this.homeGenreList.adapter = genreAdapter
        this.recyclerRecentlyAdded.adapter = booksAdapter
    }
}