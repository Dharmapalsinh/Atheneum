package com.codery.atheneum.ui.main.dashboard.issued

import androidx.navigation.fragment.navArgs
import coil.load
import com.codery.atheneum.databinding.FragmentIssuedBookViewerBinding
import com.manavtamboli.axion.binding.FloatingBottomSheetFragment

class IssuedBookViewerFragment : FloatingBottomSheetFragment<FragmentIssuedBookViewerBinding>(FragmentIssuedBookViewerBinding::class.java){

    private val args by navArgs<IssuedBookViewerFragmentArgs>()

    override fun FragmentIssuedBookViewerBinding.initialize() {
        issuedBook = args.issuedBook
        issuedBookImage.load(args.issuedBook.book.image)
        issuedBookStatus.setBackgroundColor(requireContext().getColor(args.issuedBook.backgroundColor))
    }
}