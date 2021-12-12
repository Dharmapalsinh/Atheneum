package com.codery.atheneum.ui.main.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.atheneum.myatheneum.databinding.ItemBookViewerGenreBinding
//import com.atheneum.myatheneum.ui.main.catalogue.CatalogueGenre
import com.codery.atheneum.databinding.ItemBookViewerGenreBinding
import com.codery.atheneum.ui.main.dashboard.home.CatalogueGenre
import com.codery.atheneum.ui.main.dashboard.home.DiffCatalogueGenre

class BookViewerAdapter(private val dataList: List<CatalogueGenre>) :ListAdapter<CatalogueGenre,AvailBookViewHolder>(DiffCatalogueGenre){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailBookViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBookViewerGenreBinding = ItemBookViewerGenreBinding.inflate(inflater,parent,false)
        return AvailBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvailBookViewHolder, position: Int) {
        val bookViewer : CatalogueGenre = dataList.get(position)
        holder.binding.txtBookViewerGenre.setText(bookViewer.catGenreName)
//        holder.binding.
    }


    override fun getItemCount(): Int { return dataList.size }
}

class AvailBookViewHolder(val binding: ItemBookViewerGenreBinding): RecyclerView.ViewHolder(binding.root)