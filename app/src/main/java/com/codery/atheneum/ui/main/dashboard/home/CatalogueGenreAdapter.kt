package com.codery.atheneum.ui.main.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codery.atheneum.databinding.ItemCatalogueBinding

class CatalogueGenreAdapter(val onClick : (CatalogueGenre) -> Unit) : ListAdapter<CatalogueGenre,CatalogueGenreViewHolder>(DiffCatalogueGenre) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueGenreViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCatalogueBinding = ItemCatalogueBinding.inflate(inflater,parent,false)
        return CatalogueGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogueGenreViewHolder, position: Int) {
        val genreCat : CatalogueGenre = getItem(position)
        holder.binding.txtGenre.setText(genreCat.catGenreName)
        holder.binding.txtGenre.setOnClickListener {
            onClick(genreCat)
        }
    }
}

class CatalogueGenreViewHolder(val binding : ItemCatalogueBinding) :RecyclerView.ViewHolder(binding.root)