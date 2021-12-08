package com.codery.atheneum.ui.main.dashboard.home

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentHomeBinding
import com.manavtamboli.axion.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::class.java){

    private val viewModel by viewModels<HomeViewModel>()
    private val viewModelNewBooks by viewModels<HomeViewModel>()

    private val adapterGenre = CatalogueGenreAdapter {
        Toast.makeText(requireContext(), "Genre Clicked - ${it.catGenreName}", Toast.LENGTH_SHORT)
            .show()
//        findNavController().navigate(CatalogueFragmentDirections.actionCatalogueFragmentToGenreViewerFragment(it.catGenreName,it))
    }

    private val adapterRecentlyAddedBook = RecentlyAddedAdapter {
        Toast.makeText(requireContext(), "View Clicked- ${it.newlyBook}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_dashboardFragment_to_bookViewerFragment)
    }

    override fun FragmentHomeBinding.initialize() {
        txtCatAllGenre.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Home Fragment to GenreSearch Fragment Navigation",
                Toast.LENGTH_SHORT
            ).show()
            // TODO : Navigate to Genres Fragment
        }
        txtCatAllBooks.setOnClickListener{
            Toast.makeText(requireContext(),"Home Fragment to all books Fragment Navigation",Toast.LENGTH_SHORT).show();
            // TODO : Navigate to AllBooks Fragment
        }
        this.recyclerGenre.adapter = adapterGenre
        this.recyclerRecentlyAdded.adapter = adapterRecentlyAddedBook
    }

    override fun onStart() {
        super.onStart()
        viewModel.genres.observe(viewLifecycleOwner){
            if(it == null) return@observe
            adapterGenre.submitList(it)
        }
        viewModelNewBooks.viewBooks.observe(viewLifecycleOwner){
            if(it == null) return@observe
            adapterRecentlyAddedBook.submitList(it)
        }
    }
}