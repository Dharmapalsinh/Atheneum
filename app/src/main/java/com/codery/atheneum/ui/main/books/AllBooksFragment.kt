package com.codery.atheneum.ui.main.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentAllBooks2Binding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment

class AllBooksFragment : BindingFragment<FragmentAllBooks2Binding>(FragmentAllBooks2Binding::class.java) {

    val adapter=AllBooksAdapter()

    private val viewmodel by viewModels<AllBookViewModel> (  )

    override fun FragmentAllBooks2Binding.initialize() {
        allBooksRecycler.adapter=adapter

        viewmodel.books.observe(viewLifecycleOwner){
            if(it==null)return@observe
            adapter.submitList(it)
        }
    }
}